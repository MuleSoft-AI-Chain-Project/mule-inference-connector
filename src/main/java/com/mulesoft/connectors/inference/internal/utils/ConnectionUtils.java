package com.mulesoft.connectors.inference.internal.utils;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.inference.internal.connection.ModerationImageGenerationBase;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.entity.multipart.HttpPart;
import org.mule.runtime.http.api.domain.entity.multipart.MultipartHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import static com.mulesoft.connectors.inference.internal.utils.ResponseUtils.encodeImageToBase64;

/**
 * Utility class for HTTP connection operations using Mule's HttpClient.
 */
public class ConnectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtils.class);
    private static HttpClient httpClient;


    /**
     * Build the HTTP request for the API call.
     * @param url the URL to connect to
     * @param connection the connector configuration
     * @return the configured HttpRequest
     * @throws IOException if an error occurs during request setup
     */


    public static HttpRequest buildHttpRequest(URL url, ChatCompletionBase connection) throws IOException, TimeoutException {
        HttpRequestBuilder requestBuilder = HttpRequest.builder();
       String finalUri = url.toString();

        if ("VERTEX_AI_EXPRESS".equalsIgnoreCase(connection.getInferenceType())) {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("key", connection.getApiKey());
            finalUri = url.toString() + "?" + getQueryParams(queryParams);
        }

        LOGGER.debug("Request path: {}", finalUri);

        requestBuilder
                .uri(finalUri)
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json");

        switch (connection.getInferenceType()) {
            case "ANTHROPIC":
                requestBuilder
                        .addHeader("x-api-key", connection.getApiKey())
                        .addHeader("anthropic-version", "2023-06-01");
                break;
            case "PORTKEY":
                requestBuilder
                        .addHeader("x-portkey-api-key", connection.getApiKey())
                        .addHeader("x-portkey-virtual-key", connection.getVirtualKey());
                break;
            case "AZURE_OPENAI":
                requestBuilder.addHeader("api-key", connection.getApiKey());
                break;
            case "VERTEX_AI_EXPRESS":
                //do nothing for Vertex AI Express
                // Query param already added
                break;
            case "AZURE_AI_FOUNDRY":
                requestBuilder.addHeader("api-key", connection.getApiKey());
                break;
            case "IBM_WATSON":
                // Obtain access token
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "urn:ibm:params:oauth:grant-type:apikey");
                params.put("apikey", connection.getApiKey()); // Use connection.getApiKey() instead of hardcoded
                URL tokenUrl = new URL(InferenceConstants.IBM_WATSON_Token_URL);
                String response = executeTokenRequest(tokenUrl, connection, params);
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response);
                String accessToken = jsonResponse.getString("access_token");
                requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
                break;
            case "VERTEX_AI":
            	requestBuilder.addHeader("Authorization", "Bearer " + getAccessTokenFromServiceAccountKey(connection));
                break;
            default:
                requestBuilder.addHeader("Authorization", "Bearer " + connection.getApiKey());
                break;
        }
        return requestBuilder.build();
    }

    public static HttpRequest buildHttpRequest(URL url, BaseConnection connection) {
        HttpRequestBuilder requestBuilder = HttpRequest.builder();
        String finalUri = url.toString();

        LOGGER.debug("Request path: {}", finalUri);

        requestBuilder
                .uri(finalUri)
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json");

        requestBuilder.addHeader("Authorization", "Bearer " + connection.getApiKey());

       /* switch (connection.getInferenceType()) {
            case "ANTHROPIC":
                requestBuilder
                        .addHeader("x-api-key", connection.getApiKey())
                        .addHeader("anthropic-version", "2023-06-01");
                break;
            case "PORTKEY":
                requestBuilder
                        .addHeader("x-portkey-api-key", connection.getApiKey())
                        .addHeader("x-portkey-virtual-key", connection.getVirtualKey());
                break;
            case "AZURE_OPENAI":
                requestBuilder.addHeader("api-key", connection.getApiKey());
                break;
            case "VERTEX_AI_EXPRESS":
                //do nothing for Vertex AI Express
                // Query param already added
                break;
            case "AZURE_AI_FOUNDRY":
                requestBuilder.addHeader("api-key", connection.getApiKey());
                break;
            case "IBM_WATSON":
                // Obtain access token
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "urn:ibm:params:oauth:grant-type:apikey");
                params.put("apikey", connection.getApiKey()); // Use connection.getApiKey() instead of hardcoded
                URL tokenUrl = new URL(InferenceConstants.IBM_WATSON_Token_URL);
                String response = executeTokenRequest(tokenUrl, connection, params);
                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response);
                String accessToken = jsonResponse.getString("access_token");
                requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
                break;
            case "VERTEX_AI":
                requestBuilder.addHeader("Authorization", "Bearer " + getAccessTokenFromServiceAccountKey(connection));
                break;
            default:
                requestBuilder.addHeader("Authorization", "Bearer " + connection.getApiKey());
                break;
        }*/

        return requestBuilder.build();
    }

    public static HttpRequest buildHttpRequest(URL url, TextGenerationConnection connection) {

        HttpRequestBuilder requestBuilder = HttpRequest.builder();

        String finalUri = url.toString();
        Optional.ofNullable(connection.getQueryParams())
                .ifPresent(map -> map.forEach(requestBuilder::addQueryParam));
        Optional.ofNullable(connection.getAdditionalHeaders())
                .ifPresent(map -> map.forEach(requestBuilder::addHeader));

        LOGGER.debug("Request path: {}", finalUri);

        return requestBuilder
                .uri(finalUri)
                .method("POST")
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json")
                .build();
    }

    /**
     * Get request options based on configuration.
     * @param connection the connector configuration
     * @return the configured HttpRequestOptions
     */
    public static HttpRequestOptions getRequestOptions( ChatCompletionBase connection) {
        return HttpRequestOptions.builder()
                .responseTimeout(String.valueOf(connection.getTimeout()) != null ? Integer.parseInt(String.valueOf(connection.getTimeout())) : 600000)
                .followsRedirect(true)
                .build();
    }

    public static HttpRequestOptions getRequestOptions( TextGenerationConnection connection) {
        return HttpRequestOptions.builder()
                .responseTimeout(String.valueOf(connection.getTimeout()) != null ? Integer.parseInt(String.valueOf(connection.getTimeout())) : 600000)
                .followsRedirect(true)
                .build();
    }

    public static HttpRequestOptions getRequestOptions(BaseConnection connection) {
        return HttpRequestOptions.builder()
                .responseTimeout(String.valueOf(connection.getTimeout()) != null ? Integer.parseInt(String.valueOf(connection.getTimeout())) : 600000)
                .followsRedirect(true)
                .build();
    }

    /**
     * Get the static appropriate URL for chat completion based on the configuration.
     * @param connection the connector configuration
     * @return the URL for the chat completion endpoint
     * @throws MalformedURLException if the URL is invalid
     */
    public static URL getConnectionURLChatCompletion(ChatCompletionBase connection) throws MalformedURLException {
        switch (connection.getInferenceType()) {
            case "PORTKEY":
                return new URL(InferenceConstants.PORTKEY_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "GROQ":
                return new URL(InferenceConstants.GROQ_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "HUGGING_FACE":
                return new URL(InferenceConstants.HUGGINGFACE_URL + "/models/" + connection.getModelName() + "/v1" + InferenceConstants.CHAT_COMPLETIONS);
            case "OPENROUTER":
                return new URL(InferenceConstants.OPENROUTER_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "GITHUB":
                return new URL(InferenceConstants.GITHUB_MODELS_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "OLLAMA":
                return new URL(connection.getOllamaUrl() + InferenceConstants.CHAT_COMPLETIONS_OLLAMA);
            case "XINFERENCE":
                return new URL(connection.getxinferenceUrl() + InferenceConstants.CHAT_COMPLETIONS);
            case "CEREBRAS":
                return new URL(InferenceConstants.CEREBRAS_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "NVIDIA":
                return new URL(InferenceConstants.NVIDIA_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "FIREWORKS":
                return new URL(InferenceConstants.FIREWORKS_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "TOGETHER":
                return new URL(InferenceConstants.TOGETHER_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "DEEPINFRA":
                return new URL(InferenceConstants.DEEPINFRA_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "PERPLEXITY":
                return new URL(InferenceConstants.PERPLEXITY_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "XAI":
                return new URL(InferenceConstants.X_AI_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "OPENAI":
                return new URL(InferenceConstants.OPEN_AI_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "MISTRAL":
                return new URL(InferenceConstants.MISTRAL_AI_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "ANTHROPIC":
                return new URL(InferenceConstants.ANTHROPIC_URL + "/" + InferenceConstants.MESSAGES);
            case "AI21LABS":
                return new URL(InferenceConstants.AI21LABS_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "COHERE":
                return new URL(InferenceConstants.COHERE_URL + InferenceConstants.CHAT_COMPLETIONS_OLLAMA);
            case "AZURE_OPENAI":
                String urlStr = InferenceConstants.AZURE_OPENAI_URL + InferenceConstants.CHAT_COMPLETIONS_AZURE;
                urlStr = urlStr
                        .replace("{resource-name}", connection.getAzureOpenaiResourceName())
                        .replace("{deployment-id}", connection.getAzureOpenaiDeploymentId());
                return new URL(urlStr);
            case "VERTEX_AI_EXPRESS":
                String vertexAIExpressUrlStr = InferenceConstants.VERTEX_AI_EXPRESS_URL + InferenceConstants.GENERATE_CONTENT_VERTEX_AI_GEMINI;
                vertexAIExpressUrlStr = vertexAIExpressUrlStr
                    .replace("{MODEL_ID}", connection.getModelName());
                return new URL(vertexAIExpressUrlStr);
            case "VERTEX_AI":
            	String provider = ProviderUtils.getProviderByModel(connection.getModelName());
            	String vertexAIUrlStr = "";
            	switch (provider) {
	                case "Google":
	                	vertexAIUrlStr = InferenceConstants.VERTEX_AI_GEMINI_URL + InferenceConstants.GENERATE_CONTENT_VERTEX_AI_GEMINI;
	                    vertexAIUrlStr = vertexAIUrlStr
	                    	.replace("{LOCATION_ID}", connection.getVertexAILocationId())
	                    	.replace("{PROJECT_ID}", connection.getVertexAIProjectId())
	                        .replace("{MODEL_ID}", connection.getModelName());
	                    return new URL(vertexAIUrlStr);
	  
	                case "Anthropic":
	                	vertexAIUrlStr = InferenceConstants.VERTEX_AI_ANTHROPIC_URL + InferenceConstants.GENERATE_CONTENT_VERTEX_AI_ANTHROPIC;
	                    vertexAIUrlStr = vertexAIUrlStr
	                    	.replace("{LOCATION_ID}", connection.getVertexAILocationId())
	                    	.replace("{PROJECT_ID}", connection.getVertexAIProjectId())
	                        .replace("{MODEL_ID}", connection.getModelName());
	                    return new URL(vertexAIUrlStr);
	
	                case "Meta":
	                	vertexAIUrlStr = InferenceConstants.VERTEX_AI_META_URL;
	                    vertexAIUrlStr = vertexAIUrlStr
	                    	.replace("{LOCATION_ID}", connection.getVertexAILocationId())
	                    	.replace("{PROJECT_ID}", connection.getVertexAIProjectId());
	                    return new URL(vertexAIUrlStr);
	
	                default:
	                	LOGGER.error("Unknown provider. Skipping... {}", provider);
	                    // TO DO: Need to handle unknown case
	                    break;
            	}

            case "AZURE_AI_FOUNDRY":
                String aifurlStr = InferenceConstants.AZURE_AI_FOUNDRY_URL + InferenceConstants.CHAT_COMPLETIONS_AZURE_AI_FOUNDRY;
                aifurlStr = aifurlStr
                        .replace("{resource-name}", connection.getAzureAIFoundryResourceName())
                        .replace("{api-version}", connection.getAzureAIFoundryApiVersion());
                return new URL(aifurlStr);
            case "GPT4ALL":
                return new URL(connection.getGpt4All() + InferenceConstants.CHAT_COMPLETIONS);
            case "LMSTUDIO":
                return new URL(connection.getLmStudio() + InferenceConstants.CHAT_COMPLETIONS);
            case "DOCKER_MODELS":
                return new URL(connection.getDockerModelUrl() + "/engines/llama.cpp/v1" + InferenceConstants.CHAT_COMPLETIONS);
            case "DEEPSEEK":
                return new URL(InferenceConstants.DEEPSEEK_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "ZHIPU_AI":
                return new URL(InferenceConstants.ZHIPU_AI_URL + InferenceConstants.CHAT_COMPLETIONS);
            case "OPENAI_COMPATIBLE_ENDPOINT":
                return new URL(connection.getOpenAICompatibleURL() + InferenceConstants.CHAT_COMPLETIONS);
            case "IBM_WATSON":
                String ibmwurlStr = InferenceConstants.IBM_WATSON_URL + InferenceConstants.CHAT_COMPLETIONS_IBM_WATSON;
                ibmwurlStr = ibmwurlStr
                        .replace("{api-version}", connection.getIBMWatsonApiVersion());
                return new URL(ibmwurlStr);
            case "DATABRICKS":
                String dBricksUrlStr = connection.getDataBricksModelUrl() + InferenceConstants.CHAT_COMPLETIONS_DATABRICKS;
                dBricksUrlStr = dBricksUrlStr
                        .replace("{model_name}", connection.getModelName());
                return new URL(dBricksUrlStr);
            case "LLM_API":
                return new URL(InferenceConstants.LLM_API_URL + InferenceConstants.CHAT_COMPLETIONS);
            default:
                throw new MalformedURLException("Unsupported inference type: " + connection.getInferenceType());
        }
    }

    /**
     * Get the appropriate URL for image generation based on the configuration.
     * @param connection the connector configuration
     * @return the URL for the image generation endpoint
     * @throws MalformedURLException if the URL is invalid
     */
    public static URL getConnectionURLImageGeneration(ChatCompletionBase connection) throws MalformedURLException {
        switch (connection.getInferenceType()) {
            case "OPENAI":
                return new URL(InferenceConstants.OPEN_AI_URL + InferenceConstants.OPENAI_GENERATE_IMAGES);
            case "HUGGING_FACE":
                return new URL(InferenceConstants.HUGGINGFACE_URL + "/models/" + connection.getModelName());
            case "STABILITY_AI":
                return new URL(InferenceConstants.STABILITY_AI_URL + InferenceConstants.STABILITY_AI_GENERATE_IMAGES);
            case "XAI":
                return new URL(InferenceConstants.X_AI_URL + InferenceConstants.OPENAI_GENERATE_IMAGES);
            default:
                throw new MalformedURLException("Unsupported inference type: " + connection.getInferenceType());
        }
    }

    /**
     * Execute a REST API call.
     * @param resourceUrl the URL to call
     * @param connection the connector configuration
     * @param payload the payload to send
     * @return the response string
     * @throws IOException if an error occurs during the API call
     */
    public static String executeREST(URL resourceUrl,  ChatCompletionBase connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }
        LOGGER.debug("Sending request to URL: {}", resourceUrl);
        LOGGER.trace("Payload: {} ", payload);
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, connection);
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            headersMap.put(key, String.join(",", values));
        });
        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();
        HttpRequestOptions options = getRequestOptions(connection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processResponse(response);
    }

    /**
     * Execute a REST API call.
     * @param resourceUrl the URL to call
     * @param connection the connector configuration
     * @param payload the payload to send
     * @return the response string
     * @throws IOException if an error occurs during the API call
     */
    @Deprecated
    public static String executeREST(URL resourceUrl, ModerationImageGenerationBase connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }

        ChatCompletionBase baseConnection = ProviderUtils.convertToBaseConnection(connection);
        //TextGenerationConfig inferenceConfig = ProviderUtils.convertToInferenceConfig(configuration);

        // Build initial request for headers and URI
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, baseConnection);
        // Convert MultiMap to Map
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            // Take the first value or concatenate if multiple values exist
            headersMap.put(key, String.join(",", values));
        });
        // Build final request with payload
        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));
        // Add headers individually
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();
        HttpRequestOptions options = getRequestOptions(baseConnection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processResponse(response);
    }

    public static String executeREST(URL resourceUrl, BaseConnection connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }

        ChatCompletionBase baseConnection = ProviderUtils.convertToBaseConnection(connection);
        //TextGenerationConfig inferenceConfig = ProviderUtils.convertToInferenceConfig(configuration);

        // Build initial request for headers and URI
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, connection);
        // Convert MultiMap to Map
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            // Take the first value or concatenate if multiple values exist
            headersMap.put(key, String.join(",", values));
        });
        // Build final request with payload
        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));
        // Add headers individually
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();
        HttpRequestOptions options = getRequestOptions(baseConnection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processResponse(response);
    }

    /**
     * Execute a REST API call.
     * @param resourceUrl the URL to call
     * @param connection the connector configuration
     * @param payload the payload to send
     * @return the response string
     * @throws IOException if an error occurs during the API call
     */
    public static String executeREST(URL resourceUrl, TextGenerationConnection connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }
        LOGGER.debug("Sending request to URL: {}", resourceUrl);
        LOGGER.trace("Payload: {} ", payload);
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, connection);
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            headersMap.put(key, String.join(",", values));
        });
        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();
        HttpRequestOptions options = getRequestOptions(connection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processResponse(response);
    }

    public static String executeRestImageGeneration(URL resourceUrl, BaseConnection connection, String payload ) throws IOException, TimeoutException {
        String response = "";

        if ((ProviderUtils.isHuggingFace((connection)))) {
            response = ConnectionUtils.executeRESTHuggingFaceImage(resourceUrl, connection, payload.toString());
        } else if (ProviderUtils.isStabilityAI(connection)) {
            response = executeRESTStabilityAIImage(resourceUrl, connection, payload);
        } else {
            response = ConnectionUtils.executeREST(resourceUrl, connection, payload.toString());
        }
        return response;
    }

    /**
     * Execute a REST API call for Hugging Face image generation.
     * @param resourceUrl the URL to call
     * @param connection the connector configuration
     * @param payload the payload to send
     * @return the response string
     * @throws IOException if an error occurs during the API call
     */
    public static String executeRESTHuggingFaceImage(URL resourceUrl, BaseConnection connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }
        // Build initial request for headers and URI
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, connection);
        // Convert MultiMap to Map
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            // Take the first value or concatenate if multiple values exist
            headersMap.put(key, String.join(",", values));
        });
        // Build final request with payload
        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));
        // Add headers individually
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();
        HttpRequestOptions options = getRequestOptions(connection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized in BaseConnection");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processHuggingFaceImageResponse(response, payload);
    }



    public static String executeRESTStabilityAIImage(URL resourceUrl, BaseConnection connection, String payload) throws IOException, TimeoutException {
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource URL cannot be null");
        }
        HttpRequest initialRequest = buildHttpRequest(resourceUrl, connection);
        MultiMap<String, String> headersMultiMap = initialRequest.getHeaders();
        Map<String, String> headersMap = new HashMap<>();
        headersMultiMap.forEach((key, values) -> {
            headersMap.put(key, String.join(",", values));
        });

        // Remove any existing Content-Type to avoid conflicts
        headersMap.remove("Content-Type");
        headersMap.remove("content-type");

        JSONObject payloadJson = new JSONObject(payload);
        List<HttpPart> parts = new ArrayList<>();
        byte[] promptBytes = payloadJson.getString("prompt").getBytes(StandardCharsets.UTF_8);
        parts.add(new HttpPart("prompt", promptBytes, "text/plain", promptBytes.length));
        HttpEntity entity = new MultipartHttpEntity(parts);

        HttpRequestBuilder builder = HttpRequest.builder()
                .uri(initialRequest.getUri())
                .method(initialRequest.getMethod())
                .addHeader("Content-Type", "multipart/form-data")
                .entity(entity);
        headersMap.forEach(builder::addHeader);
        HttpRequest finalRequest = builder.build();

        HttpRequestOptions options = getRequestOptions(connection);

        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized in BaseConnection");
        }
        HttpResponse response = httpClient.send(finalRequest, options);
        return processStabilityAIImageResponse(response, payload);
    }


    /**
     * Process the HTTP response for standard REST calls.
     * @param response the HttpResponse to process
     * @return the response string
     * @throws IOException if the response indicates an error
     */
    private static String processResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();

        if (statusCode == 200) {
            return new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
        } else {
            String errorResponse = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
            LOGGER.error("API request failed with status code: {} and message: {}", statusCode, errorResponse);
            throw new IOException("API request failed with status code: " + statusCode + " and message: " + errorResponse);
        }
    }

    /**
     * Process the HTTP response for Hugging Face image generation.
     * @param response the HttpResponse to process
     * @param payload the original payload for context
     * @return the response string in JSON format
     * @throws IOException if the response indicates an error
     */
    private static String processHuggingFaceImageResponse(HttpResponse response, String payload) throws IOException {
        int statusCode = response.getStatusCode();
        JSONObject responseWrapper = new JSONObject();

        if (statusCode == 200) {
            String contentType = response.getHeaderValue("Content-Type");

            if (contentType != null && contentType.startsWith("image/")) {
                byte[] responseBytes = response.getEntity().getBytes();
                String base64Image = encodeImageToBase64(responseBytes);

                JSONObject base64Object = new JSONObject();
                base64Object.put("b64_json", base64Image);

                JSONObject revisedPrompt = new JSONObject(payload);
                base64Object.put("revised_prompt", revisedPrompt.getString("inputs"));

                JSONArray dataArray = new JSONArray();
                dataArray.put(base64Object);

                responseWrapper.put("data", dataArray);
            }

            return responseWrapper.toString();
        } else {
            String errorResponse = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
            LOGGER.error("API request failed with status code: {} and message: {}", statusCode, errorResponse);
            throw new IOException("API request failed with status code: " + statusCode + " and message: " + errorResponse);
        }
    }


    private static String processStabilityAIImageResponse(HttpResponse response, String payload) throws IOException {
        int statusCode = response.getStatusCode();
        JSONObject responseWrapper = new JSONObject();

        if (statusCode == 200) {
            String responseBody = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
            JSONObject stabilityResponse = new JSONObject(responseBody);
            String contentType = response.getHeaderValue("Content-Type");

            if (contentType != null && contentType.contains("application/json") && stabilityResponse.has("image")) {
                String base64Image = stabilityResponse.getString("image");
                // Clean base64 string if it contains data URI prefix
                if (base64Image.startsWith("data:image")) {
                    base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
                }
                // Log base64 length for debugging
                LOGGER.debug("Base64 image length: {}", base64Image.length());
                JSONObject base64Object = new JSONObject();
                base64Object.put("b64_json", base64Image);
                JSONObject payloadJson = new JSONObject(payload);

                base64Object.put("revised_prompt", payloadJson.getString("prompt"));
                JSONArray dataArray = new JSONArray();
                dataArray.put(base64Object);
                responseWrapper.put("data", dataArray);
            } else {
                LOGGER.error("Unexpected response format: Content-Type is {} and response body is {}", contentType, responseBody);
                throw new IOException("Unexpected response format from Stability AI API");
            }
            return responseWrapper.toString();
        } else {
            String errorResponse = new String(response.getEntity().getBytes(), StandardCharsets.UTF_8);
            LOGGER.error("API request failed with status code: {} and message: {}", statusCode, errorResponse);
            throw new IOException("API request failed with status code: " + statusCode + " and message: " + errorResponse);
        }
    }

    /**
     * Utility method to encode query parameters.
     * @param params the query parameters
     * @return the encoded query string
     * @throws UnsupportedEncodingException if encoding fails
     */
    public static String getQueryParams(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (query.length() > 0) {
                query.append("&");
            }
            query.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return query.toString();
    }

    //get access token from google service acc key file	
    public static String getAccessTokenFromServiceAccountKey(ChatCompletionBase connection) throws IOException {
    	FileInputStream serviceAccountStream = new FileInputStream(connection.getVertexAIServiceAccountKey());
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(serviceAccountStream)
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        credentials.refreshIfExpired();
        String token = credentials.getAccessToken().getTokenValue();
        LOGGER.debug("gcp access token {}", token);
        return token;
    	        
    }

    /**
     * Read response bytes from an input stream.
     * @param inputStream the input stream
     * @return the byte array
     * @throws IOException if reading fails
     */
    public static byte[] readResponseBytes(java.io.InputStream inputStream) throws IOException {
        if (inputStream == null) return new byte[0];

        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] data = new byte[4096];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }
    }
    
    
  


    /**
     * Execute a token request using MuleHttpClient
     * @param url the URL to connect to
     * @param connection the BaseConnection providing HttpClient and timeout
     * @param params the form parameters (e.g., client_id, client_secret, grant_type)
     * @return the response body as a String
     * @throws IOException if an error occurs during the request
     * @throws TimeoutException if the request times out
     */
    public static String executeTokenRequest(URL url, ChatCompletionBase connection, Map<String, String> params) throws IOException, TimeoutException {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }

        // Build URL-encoded payload
        String payload = getURLEncodedData(params);
        LOGGER.debug("Token request payload: {}", payload);

        // Build HttpRequest
        HttpRequestBuilder requestBuilder = HttpRequest.builder()
                .uri(url.toString())
                .method("POST")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0")
                .addHeader("Accept", "application/json")
                .entity(new ByteArrayHttpEntity(payload.getBytes(StandardCharsets.UTF_8)));

        HttpRequest request = requestBuilder.build();

        // Set request options (timeouts)
        HttpRequestOptions options = HttpRequestOptions.builder()
                .responseTimeout(connection.getTimeout() != 0 ? connection.getTimeout() : 600000) // Read timeout
                .build();

        // Execute request
        HttpClient httpClient = connection.getHttpClient();
        if (httpClient == null) {
            throw new IllegalStateException("HttpClient is not initialized in BaseConnection");
        }

        LOGGER.debug("Executing token request to: {}", url);
        HttpResponse response = httpClient.send(request, options);

        // Process response
        return processTokenResponse(response);
    }

    /**
     * Process the HTTP response for the token request
     * @param response the HttpResponse
     * @return the response body as a String
     * @throws IOException if an error occurs reading the response
     */
    private static String processTokenResponse(HttpResponse response) throws IOException {
        int statusCode = response.getStatusCode();
        HttpEntity entity = response.getEntity();
        String responseBody;

        if (entity != null && entity.getContent() != null) {
            try (InputStream content = entity.getContent();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = content.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                responseBody = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
            }
        } else {
            responseBody = "";
        }

        if (statusCode >= 200 && statusCode < 300) {
            LOGGER.debug("Token request successful, response: {}", responseBody);
            return responseBody;
        } else {
            LOGGER.error("Token request failed with status {}: {}", statusCode, responseBody);
            throw new IOException("Token request failed with status " + statusCode + ": " + responseBody);
        }
    }

    /**
     * Build URL-encoded form data from parameters
     * @param params the parameters to encode
     * @return the URL-encoded string
     * @throws IOException if encoding fails
     */
    private static String getURLEncodedData(Map<String, String> params) throws IOException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
