package com.mulesoft.connectors.inference.internal.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.vertexai.VertexAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.entity.ByteArrayHttpEntity;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Utility class for HTTP connection operations using Mule's HttpClient.
 */
public class ConnectionUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionUtils.class);


    //get access token from google service acc key file
    public static String getAccessTokenFromServiceAccountKey(TextGenerationConnection connection) {

        try {
            VertexAITextGenerationConnection textGenerationConnection = (VertexAITextGenerationConnection) connection;
            FileInputStream serviceAccountStream = new FileInputStream(textGenerationConnection.getVertexAIServiceAccountKey());
            GoogleCredentials credentials = GoogleCredentials
                    .fromStream(serviceAccountStream)
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

            credentials.refreshIfExpired();
            String token = credentials.getAccessToken().getTokenValue();
            logger.debug("gcp access token {}", token);
            return token;
        } catch (IOException e) {
            throw new ModuleException("Error fetching the token for ibm watson.", InferenceErrorType.INVALID_CONNECTION, e);
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
    public static String executeTokenRequest(URL url, TextGenerationConnection connection, Map<String, String> params) throws IOException, TimeoutException {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }

        // Build URL-encoded payload
        String payload = getURLEncodedData(params);
        logger.debug("Token request payload: {}", payload);

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

        logger.debug("Executing token request to: {}", url);
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
            logger.debug("Token request successful, response: {}", responseBody);
            return responseBody;
        } else {
            logger.error("Token request failed with status {}: {}", statusCode, responseBody);
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
        return !resultString.isEmpty()
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}
