package com.mulesoft.connectors.inference.internal.utils;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.metadata.TokenUsage;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.TokenHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

/**
 * Utility class for processing API responses.
 */
public class ResponseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    /**
     * Process the response from the LLM API
     * @param response the response string from the API
     * @param configuration the connector configuration
     * @param isToolsResponse whether this is a tools response
     * @return result containing the LLM response
     * @throws Exception if an error occurs during processing
     */
    public static Result<InputStream, LLMResponseAttributes> processResponse(    	
            String response, ChatCompletionBase configuration, boolean isToolsResponse) throws Exception {

               	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

        JSONObject root = new JSONObject(response);
        ResponseInfo responseInfo = extractResponseInfo(root, configuration);
        String content = null;

        // Process tool calls if needed
        JSONArray toolCalls = new JSONArray();
        if (isToolsResponse && responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
            toolCalls = processToolCalls(responseInfo.message.getJSONArray("tool_calls"));
        }

      if (isToolsResponse && root.has("tool_calls") && !root.isNull("tool_calls")) {
          toolCalls = processToolCalls(root.getJSONArray("tool_calls"));
      }

        // Handle Anthropic tool_use for tools responses
        if (isToolsResponse && ProviderUtils.isAnthropic(configuration)) {
            JSONArray toolsCallAnthropic = extractAnthropicToolCalls(root.getJSONArray("content"));
            if (!toolsCallAnthropic.isEmpty()) {
                responseInfo.message.put("tool_calls", toolsCallAnthropic);
            }
        }

        // Handle Vertex AI tools responses (functionCall)
        if (isToolsResponse && ("Google".equalsIgnoreCase(provider))) {
        	//for Google/Gemini
            JSONArray functionCalls = extractVertexAIFunctionCalls(root);
            if (!functionCalls.isEmpty()) {
                responseInfo.message.put("tool_calls", functionCalls);
            }
        }

        if (ProviderUtils.isCohere(configuration)) {
            JSONArray contentArray = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getJSONArray("content")
                    : null;

            if (contentArray != null && contentArray.length() > 0) {
                JSONObject firstContent = contentArray.getJSONObject(0); // Get the first item in the array
                if (firstContent.has("text") && !firstContent.isNull("text")) {
                    content = firstContent.getString("text"); // Extract the "text" field
                }
            }
        } else if (("Google".equalsIgnoreCase(provider))) {
            	 //for google/gemini
            content = responseInfo.message.has("text") && !responseInfo.message.isNull("text")
                    ? responseInfo.message.getString("text") : null;

        } else {
            content = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getString("content") : null;
        }

        TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(InferenceConstants.RESPONSE, content);

        if (isToolsResponse) {
            JSONArray finalToolCalls = toolCalls;
            if (responseInfo.message.has("tool_calls")
                    && !responseInfo.message.isNull("tool_calls")) {
                if (!responseInfo.message.getJSONArray("tool_calls").isEmpty()) {
                    finalToolCalls = responseInfo.message.getJSONArray("tool_calls");
                }
            }
            jsonObject.put(InferenceConstants.TOOLS, finalToolCalls);
        }

        Map<String, String> responseAttributes = new HashMap<>();
        responseAttributes.put(InferenceConstants.FINISH_REASON, responseInfo.finishReason);
        responseAttributes.put(InferenceConstants.MODEL, responseInfo.model);
        responseAttributes.put(InferenceConstants.ID_STRING, responseInfo.id);

        return ResponseHelper.createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
    }

    public static Result<InputStream, LLMResponseAttributes> processResponse(
            String response, TextGenerationConnection connection, boolean isToolsResponse) throws Exception {

        JSONObject root = new JSONObject(response);
        ResponseInfo responseInfo = extractResponseInfo(root, connection);
        String content = null;

        // Process tool calls if needed
        JSONArray toolCalls = new JSONArray();
        if (isToolsResponse && responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
            toolCalls = processToolCalls(responseInfo.message.getJSONArray("tool_calls"));
        }

        if (isToolsResponse && root.has("tool_calls") && !root.isNull("tool_calls")) {
            toolCalls = processToolCalls(root.getJSONArray("tool_calls"));
        }

  /*      // Handle Anthropic tool_use for tools responses
        if (isToolsResponse && ProviderUtils.isAnthropic(connection)) {
            JSONArray toolsCallAnthropic = extractAnthropicToolCalls(root.getJSONArray("content"));
            if (!toolsCallAnthropic.isEmpty()) {
                responseInfo.message.put("tool_calls", toolsCallAnthropic);
            }
        }

        // Handle Vertex AI tools responses (functionCall)
        if (isToolsResponse && ("Google".equalsIgnoreCase(provider))) {
            //for Google/Gemini
            JSONArray functionCalls = extractVertexAIFunctionCalls(root);
            if (!functionCalls.isEmpty()) {
                responseInfo.message.put("tool_calls", functionCalls);
            }
        }

        if (ProviderUtils.isCohere(connection)) {
            JSONArray contentArray = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getJSONArray("content")
                    : null;

            if (contentArray != null && contentArray.length() > 0) {
                JSONObject firstContent = contentArray.getJSONObject(0); // Get the first item in the array
                if (firstContent.has("text") && !firstContent.isNull("text")) {
                    content = firstContent.getString("text"); // Extract the "text" field
                }
            }
        } else if (("Google".equalsIgnoreCase(provider))) {
            //for google/gemini
            content = responseInfo.message.has("text") && !responseInfo.message.isNull("text")
                    ? responseInfo.message.getString("text") : null;

        } else {*/
            content = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getString("content") : null;
       // }

        TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(InferenceConstants.RESPONSE, content);

        if (isToolsResponse) {
            JSONArray finalToolCalls = toolCalls;
            if (responseInfo.message.has("tool_calls")
                    && !responseInfo.message.isNull("tool_calls")) {
                if (!responseInfo.message.getJSONArray("tool_calls").isEmpty()) {
                    finalToolCalls = responseInfo.message.getJSONArray("tool_calls");
                }
            }
            jsonObject.put(InferenceConstants.TOOLS, finalToolCalls);
        }

        Map<String, String> responseAttributes = new HashMap<>();
        responseAttributes.put(InferenceConstants.FINISH_REASON, responseInfo.finishReason);
        responseAttributes.put(InferenceConstants.MODEL, responseInfo.model);
        responseAttributes.put(InferenceConstants.ID_STRING, responseInfo.id);

        return ResponseHelper.createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
    }

    public static Result<InputStream, LLMResponseAttributes> processResponse(
            String response, ChatCompletionBase configuration, boolean isToolsResponse, JSONArray toolExecutionResult) throws Exception {

        String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

        JSONObject root = new JSONObject(response);
        ResponseInfo responseInfo = extractResponseInfo(root, configuration);
        String content = null;

        // Process tool calls if needed
        JSONArray toolCalls = new JSONArray();
        if (isToolsResponse && responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
            toolCalls = processToolCalls(responseInfo.message.getJSONArray("tool_calls"));
        }

        if (isToolsResponse && root.has("tool_calls") && !root.isNull("tool_calls")) {
            toolCalls = processToolCalls(root.getJSONArray("tool_calls"));
        }

        // Handle Anthropic tool_use for tools responses
        if (isToolsResponse && ProviderUtils.isAnthropic(configuration)) {
            JSONArray toolsCallAnthropic = extractAnthropicToolCalls(root.getJSONArray("content"));
            if (!toolsCallAnthropic.isEmpty()) {
                responseInfo.message.put("tool_calls", toolsCallAnthropic);
            }
        }

        // Handle Vertex AI tools responses (functionCall)
        if (isToolsResponse && ("Google".equalsIgnoreCase(provider))) {
            // for Google/Gemini
            JSONArray functionCalls = extractVertexAIFunctionCalls(root);
            if (!functionCalls.isEmpty()) {
                responseInfo.message.put("tool_calls", functionCalls);
            }
        }

        if (ProviderUtils.isCohere(configuration)) {
            JSONArray contentArray = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getJSONArray("content")
                    : null;

            if (contentArray != null && contentArray.length() > 0) {
                JSONObject firstContent = contentArray.getJSONObject(0); // Get the first item in the array
                if (firstContent.has("text") && !firstContent.isNull("text")) {
                    content = firstContent.getString("text"); // Extract the "text" field
                }
            }
        } else if (("Google".equalsIgnoreCase(provider))) {
            // for google/gemini
            content = responseInfo.message.has("text") && !responseInfo.message.isNull("text")
                    ? responseInfo.message.getString("text") : null;
        } else {
            content = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getString("content") : null;
        }

        TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(InferenceConstants.RESPONSE, content);

        if (isToolsResponse) {
            JSONArray finalToolCalls = toolCalls;
            // Check if we have tool calls in the message from Anthropic
            if (responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
                if (!responseInfo.message.getJSONArray("tool_calls").isEmpty()){
                    finalToolCalls = responseInfo.message.getJSONArray("tool_calls");
                }
            }
            jsonObject.put(InferenceConstants.TOOLS, finalToolCalls);

            // Add toolsExecutionReport if toolExecutionResult is provided
            if (toolExecutionResult != null && !toolExecutionResult.isEmpty()) {
                jsonObject.put("toolsExecutionReport", toolExecutionResult);
            }
        }

        Map<String, String> responseAttributes = new HashMap<>();
        responseAttributes.put(InferenceConstants.FINISH_REASON, responseInfo.finishReason);
        responseAttributes.put(InferenceConstants.MODEL, responseInfo.model);
        responseAttributes.put(InferenceConstants.ID_STRING, responseInfo.id);

        return ResponseHelper.createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
    }

    public static Result<InputStream, LLMResponseAttributes> processResponse(
            String response, TextGenerationConnection connection, boolean isToolsResponse, JSONArray toolExecutionResult) throws Exception {

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        JSONObject root = new JSONObject(response);
        ResponseInfo responseInfo = extractResponseInfo(root, connection);
        String content = null;

        // Process tool calls if needed
        JSONArray toolCalls = new JSONArray();
        if (isToolsResponse && responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
            toolCalls = processToolCalls(responseInfo.message.getJSONArray("tool_calls"));
        }

        if (isToolsResponse && root.has("tool_calls") && !root.isNull("tool_calls")) {
            toolCalls = processToolCalls(root.getJSONArray("tool_calls"));
        }

        // Handle Anthropic tool_use for tools responses
       /* if (isToolsResponse && ProviderUtils.isAnthropic(connection)) {
            JSONArray toolsCallAnthropic = extractAnthropicToolCalls(root.getJSONArray("content"));
            if (!toolsCallAnthropic.isEmpty()) {
                responseInfo.message.put("tool_calls", toolsCallAnthropic);
            }
        }*/

        // Handle Vertex AI tools responses (functionCall)
        if (isToolsResponse && ("Google".equalsIgnoreCase(provider))) {
            // for Google/Gemini
            JSONArray functionCalls = extractVertexAIFunctionCalls(root);
            if (!functionCalls.isEmpty()) {
                responseInfo.message.put("tool_calls", functionCalls);
            }
        }

       /* if (ProviderUtils.isCohere(connection)) {
            JSONArray contentArray = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getJSONArray("content")
                    : null;

            if (contentArray != null && contentArray.length() > 0) {
                JSONObject firstContent = contentArray.getJSONObject(0); // Get the first item in the array
                if (firstContent.has("text") && !firstContent.isNull("text")) {
                    content = firstContent.getString("text"); // Extract the "text" field
                }
            }
        } else*/ if (("Google".equalsIgnoreCase(provider))) {
            // for google/gemini
            content = responseInfo.message.has("text") && !responseInfo.message.isNull("text")
                    ? responseInfo.message.getString("text") : null;
        } else {
            content = responseInfo.message.has("content") && !responseInfo.message.isNull("content")
                    ? responseInfo.message.getString("content") : null;
        }

        TokenUsage tokenUsage = TokenHelper.parseUsageFromResponse(response);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(InferenceConstants.RESPONSE, content);

        if (isToolsResponse) {
            JSONArray finalToolCalls = toolCalls;
            // Check if we have tool calls in the message from Anthropic
            if (responseInfo.message.has("tool_calls") && !responseInfo.message.isNull("tool_calls")) {
                if (!responseInfo.message.getJSONArray("tool_calls").isEmpty()){
                    finalToolCalls = responseInfo.message.getJSONArray("tool_calls");
                }
            }
            jsonObject.put(InferenceConstants.TOOLS, finalToolCalls);

            // Add toolsExecutionReport if toolExecutionResult is provided
            if (toolExecutionResult != null && !toolExecutionResult.isEmpty()) {
                jsonObject.put("toolsExecutionReport", toolExecutionResult);
            }
        }

        Map<String, String> responseAttributes = new HashMap<>();
        responseAttributes.put(InferenceConstants.FINISH_REASON, responseInfo.finishReason);
        responseAttributes.put(InferenceConstants.MODEL, responseInfo.model);
        responseAttributes.put(InferenceConstants.ID_STRING, responseInfo.id);

        return ResponseHelper.createLLMResponse(jsonObject.toString(), tokenUsage, responseAttributes);
    }

    /**
     * Process the response from the LLM API for standard chat operations
     * @param response the response string from the API
     * @param configuration the connector configuration
     * @return result containing the LLM response
     * @throws Exception if an error occurs during processing
     */
    public static Result<InputStream, LLMResponseAttributes> processLLMResponse(
            String response, ChatCompletionBase configuration) throws Exception {
        return processResponse(response, configuration, false);
    }

    public static Result<InputStream, LLMResponseAttributes> processLLMResponse(
            String response, TextGenerationConnection connection) throws Exception {
        return processResponse(response, connection, false);
    }

    public static Result<InputStream, LLMResponseAttributes> processToolsResponse(
            String response, TextGenerationConnection connection) throws Exception {
        return processResponse(response, connection, true);
    }

    public static Result<InputStream, LLMResponseAttributes> processToolsResponse(
            String response, TextGenerationConnection connection, JSONArray toolExecutionResult) throws Exception {
        return processResponse(response, connection, true, toolExecutionResult);
    }

    /**
     * Container class for response information
     */
    private static class ResponseInfo {
        String model;
        String id;
        JSONObject message;
        String finishReason;
        String text = "";
    }

    private static ResponseInfo extractResponseInfo(JSONObject root, TextGenerationConnection connection) {
        ResponseInfo info = new ResponseInfo();

        String provider = ProviderUtils.getProviderByModel(connection.getModelName());

        info.model = /*!("AI21LABS".equals(configuration.getInferenceType())

                || "COHERE".equals(configuration.getInferenceType())
                || "VERTEX_AI_EXPRESS".equals(configuration.getInferenceType())
                || "VERTEX_AI".equals(configuration.getInferenceType())
                || ("VERTEX_AI".equals(configuration.getInferenceType()) && "Anthropic".equalsIgnoreCase(provider))
        )
                ? root.getString("model")   //if model is not AI21LABS or COHERE or VERTEX_AI_EXPRESS or VERTEX_AI AND provider is Anthropic
                : */connection.getModelName();

        /*if (ProviderUtils.isOllama(configuration) || ProviderUtils.isllamaAPI(configuration)) {
            info.id = null;
        } else if ((ProviderUtils.isVertexAIExpress(configuration) || ProviderUtils.isVertexAI(configuration)) &&
                !"Anthropic".equalsIgnoreCase(provider) &&
                !"Meta".equalsIgnoreCase(provider)) {

            info.id = root.getString("responseId");
        } else {*/
            info.id = root.getString("id");
       // }

        info.message = new JSONObject();

       /* if (ProviderUtils.isOllama(connection)) {
            info.message = root.getJSONObject("message");
            info.finishReason = root.getString("done_reason");
        } else if (ProviderUtils.isCohere(connection)) {
            info.message = root.getJSONObject("message");
            info.finishReason = root.getString("finish_reason");
        } else if (ProviderUtils.isAnthropic(connection) || "Anthropic".equalsIgnoreCase(provider)) {
            info.finishReason = root.getString("stop_reason");

            // Extract text from content array
            if (root.has("content") && root.getJSONArray("content").length() > 0) {
                JSONArray contentArray = root.getJSONArray("content");
                for (int i = 0; i < contentArray.length(); i++) {
                    JSONObject contentItem = contentArray.getJSONObject(i);
                    if ("text".equals(contentItem.getString("type")) && info.text.isEmpty()) {
                        info.text = contentItem.getString("text");
                        break;
                    }
                }
            }

            info.message = new JSONObject();
            info.message.put("content", info.text);
        } else if ((ProviderUtils.isVertexAIExpress(configuration) || ProviderUtils.isVertexAI(configuration)) && !"Meta".equalsIgnoreCase(provider)) {
            //for google models
            // Extract candidates array
            JSONArray candidatesArray = root.getJSONArray("candidates");

            // Ensure at least one candidate exists
            if (candidatesArray.length() > 0) {
                JSONObject firstCandidate = candidatesArray.getJSONObject(0);

                // Extract finishReason
                info.finishReason = firstCandidate.optString("finishReason", "Unknown");

                // Extract text from content.parts array
                JSONObject content = firstCandidate.getJSONObject("content");
                JSONArray partsArray = content.getJSONArray("parts");
                info.message = partsArray.getJSONObject(0);

            } else {
                LOGGER.debug("No candidates found in the response from provider: {}", configuration.getInferenceType());
            }


        } else {*/
            // Default case for other models (OpenAI, etc.)
            JSONArray choicesArray = root.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);


            info.finishReason = /*ProviderUtils.isNvidia(connection) ? "" :*/ firstChoice.getString("finish_reason");
            info.message = firstChoice.getJSONObject("message");
       // }
        return info;
    }

    /**
     * Extract basic information from the response
     * @param root the root JSON object of the response
     * @param configuration the connector configuration
     * @return ResponseInfo containing the extracted information
     */
    private static ResponseInfo extractResponseInfo(JSONObject root, ChatCompletionBase configuration) {
        ResponseInfo info = new ResponseInfo();
        
    	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

    	info.model = !("AI21LABS".equals(configuration.getInferenceType())

                || "COHERE".equals(configuration.getInferenceType())
                || "VERTEX_AI_EXPRESS".equals(configuration.getInferenceType())
                || "VERTEX_AI".equals(configuration.getInferenceType())
                || ("VERTEX_AI".equals(configuration.getInferenceType()) && "Anthropic".equalsIgnoreCase(provider))
                )
                ? root.getString("model")   //if model is not AI21LABS or COHERE or VERTEX_AI_EXPRESS or VERTEX_AI AND provider is Anthropic
                : configuration.getModelName();

        if (ProviderUtils.isOllama(configuration) || ProviderUtils.isllamaAPI(configuration)) {
            info.id = null;
        } else if ((ProviderUtils.isVertexAIExpress(configuration) || ProviderUtils.isVertexAI(configuration)) &&
        	    !"Anthropic".equalsIgnoreCase(provider) &&
        	    !"Meta".equalsIgnoreCase(provider)) {
        	    
        	    info.id = root.getString("responseId");
        } else {
        	    info.id = root.getString("id");
        }

        info.message = new JSONObject();

        if (ProviderUtils.isOllama(configuration)) {
            info.message = root.getJSONObject("message");
            info.finishReason = root.getString("done_reason");
        } else if (ProviderUtils.isCohere(configuration)) {
            info.message = root.getJSONObject("message");
            info.finishReason = root.getString("finish_reason");
        } else if (ProviderUtils.isAnthropic(configuration) || "Anthropic".equalsIgnoreCase(provider)) {
            info.finishReason = root.getString("stop_reason");

            // Extract text from content array
            if (root.has("content") && root.getJSONArray("content").length() > 0) {
                JSONArray contentArray = root.getJSONArray("content");
                for (int i = 0; i < contentArray.length(); i++) {
                    JSONObject contentItem = contentArray.getJSONObject(i);
                    if ("text".equals(contentItem.getString("type")) && info.text.isEmpty()) {
                        info.text = contentItem.getString("text");
                        break;
                    }
                }
            }

            info.message = new JSONObject();
            info.message.put("content", info.text);
        } else if ((ProviderUtils.isVertexAIExpress(configuration) || ProviderUtils.isVertexAI(configuration)) && !"Meta".equalsIgnoreCase(provider)) {
        	//for google models
        	// Extract candidates array
            JSONArray candidatesArray = root.getJSONArray("candidates");

            // Ensure at least one candidate exists
            if (candidatesArray.length() > 0) {
                JSONObject firstCandidate = candidatesArray.getJSONObject(0);

                // Extract finishReason
                info.finishReason = firstCandidate.optString("finishReason", "Unknown");

                // Extract text from content.parts array
                JSONObject content = firstCandidate.getJSONObject("content");
                JSONArray partsArray = content.getJSONArray("parts");
                info.message = partsArray.getJSONObject(0);
                
            } else {
            	LOGGER.debug("No candidates found in the response from provider: {}", configuration.getInferenceType());
            }
        	
        	
        } else {
            // Default case for other models (OpenAI, etc.)
            JSONArray choicesArray = root.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);


            info.finishReason = ProviderUtils.isNvidia(configuration) ? "" : firstChoice.getString("finish_reason");
            info.message = firstChoice.getJSONObject("message");
        }
        return info;
    }
    
    
    /**
     * Extract function calls from Vertex AI format response
     * @param root the JSONObject from Vertex AI response
     * @return JSONArray of function calls in a standardized format
     */
    private static JSONArray extractVertexAIFunctionCalls(JSONObject root) {
        JSONArray functionCalls = new JSONArray();
        
        if (!root.has("candidates")) {
            return functionCalls; // Return empty array if no candidates
        }

        JSONArray candidates = root.getJSONArray("candidates");

        for (int i = 0; i < candidates.length(); i++) {
            JSONObject candidate = candidates.getJSONObject(i);
            if (!candidate.has("content")) continue;

            JSONObject content = candidate.getJSONObject("content");
            if (!content.has("parts")) continue;

            JSONArray parts = content.getJSONArray("parts");

            for (int j = 0; j < parts.length(); j++) {
                JSONObject part = parts.getJSONObject(j);
                if (part.has("functionCall")) {
                    functionCalls.put(part.getJSONObject("functionCall"));
                }
            }
        }

        return functionCalls;

     }

    /**
     * Extract tool calls from Anthropic format response
     * @param contentArray the content array from Anthropic response
     * @return JSONArray of tool calls in a standardized format
     */
    private static JSONArray extractAnthropicToolCalls(JSONArray contentArray) {
        JSONArray toolCalls = new JSONArray();

        for (int i = 0; i < contentArray.length(); i++) {
            JSONObject contentItem = contentArray.getJSONObject(i);
            String type = contentItem.getString("type");

            if ("tool_use".equals(type)) {
                JSONObject functionObject = new JSONObject();
                functionObject.put("name", contentItem.getString("name"));
                functionObject.put("arguments", contentItem.getJSONObject("input"));

                JSONObject toolItem = new JSONObject();
                toolItem.put("function", functionObject);
                toolItem.put("id", contentItem.getString("id"));
                toolItem.put("type", "function");

                toolCalls.put(toolItem);
            }
        }

        return toolCalls;
    }

    /**
     * Process tool calls from the LLM response
     * @param toolCalls the raw tool calls JSON array
     * @return processed tool calls JSON array
     */
    private static JSONArray processToolCalls(JSONArray toolCalls) {
        JSONArray processedToolCalls = new JSONArray();

        for (int i = 0; i < toolCalls.length(); i++) {
            JSONObject toolCall = toolCalls.getJSONObject(i);

            if (toolCall.has("function")) {
                JSONObject functionObject = toolCall.getJSONObject("function");
                if (functionObject.has("arguments")) {
                    // Try to convert "arguments" from a string to a JSON object
                    try {
                        JSONObject arguments = new JSONObject(functionObject.getString("arguments"));
                        functionObject.put("arguments", arguments);
                    } catch (Exception e) {
                        // Keep original string if not valid JSON
                        LOGGER.warn("Failed to parse function arguments as JSON: {}", e.getMessage());
                    }
                }
                
            }
            
            processedToolCalls.put(toolCall);
        }

        return processedToolCalls;
    }


    public static Result<InputStream, LLMResponseAttributes> processImageGenResponse(
            String response, BaseConnection connection) throws Exception {

        JSONObject root = new JSONObject(response);
        JSONObject jsonObject = new JSONObject();
        Map<String, String> responseAttributes = new HashMap<>();

        if ((ProviderUtils.isOpenAI(connection)
                || ProviderUtils.isHuggingFace(connection)
                || ProviderUtils.isStabilityAI(connection)
                || ProviderUtils.isXAI(connection))
                && root.has("data")) {
            JSONArray dataArray = root.getJSONArray("data");

            if (dataArray.length() > 0) {
                JSONObject firstData = dataArray.getJSONObject(0);

                String b64Json = firstData.optString("b64_json", null);
                String revisedPrompt = firstData.optString("revised_prompt", null);

                if (revisedPrompt != null) {
                    responseAttributes.put("Prompt_used", revisedPrompt);
                }

                jsonObject.put(InferenceConstants.RESPONSE, b64Json);
            }
        }

        responseAttributes.put("model", connection.getModelName());
        return ResponseHelper.createLLMResponse(jsonObject.toString(), null, responseAttributes);
    }

    public static Result<InputStream, LLMResponseAttributes> processResponse(BaseConnection connection, String llmResponse) throws ModuleException {
        try {
            JSONObject responseObject = new JSONObject();
            JSONObject llmResponseObject = new JSONObject(llmResponse);
            responseObject.put("flagged", isFlagged(connection.getInferenceType(), llmResponseObject));
            List<Map<String, Double>> categories = getCategories(llmResponseObject);
            JSONArray categoriesArray = new JSONArray();
            categories.forEach(category -> categoriesArray.put(new JSONObject(category)));
            responseObject.put("categories", categoriesArray);
            return Result.<InputStream, LLMResponseAttributes>builder()
                    .attributesMediaType(MediaType.APPLICATION_JAVA)
                    .output(toInputStream(responseObject.toString(), StandardCharsets.UTF_8))
                    .mediaType(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error processing moderation response: {}", e.getMessage(), e);
            throw new ModuleException("MODERATION ERROR", InferenceErrorType.TEXT_MODERATION_FAILURE, e);
        }
    }

    protected static List<Map<String, Double>> getCategories(JSONObject llmResponseObject) {
        JSONArray results = llmResponseObject.getJSONArray("results");
        List<Map<String, Double>> returnValueList = new ArrayList<>();
        for (Object result : results) {
            Map<String, Double> categoriesMap = new HashMap<>();
            JSONObject resultObject = (JSONObject) result;
            JSONObject categoriesObject = resultObject.getJSONObject("categories");
            JSONObject categoryScoresObject = resultObject.getJSONObject("category_scores");
            for (String key : categoriesObject.keySet()) {
                categoriesMap.put(key, categoryScoresObject.getDouble(key));
            }
            returnValueList.add(categoriesMap);
        }
        return returnValueList;
    }

    private static boolean isFlagged(String inferenceType, JSONObject response) {

        return switch (inferenceType)
        {
            case "MistralAI" -> isMistralFlagged(response);
            case "OpenAI" -> isOpenAIFlagged(response);
            default -> throw new IllegalStateException("Unexpected value: " + inferenceType);
        };
    }

    private static boolean isOpenAIFlagged(JSONObject response) {
        JSONArray results = response.getJSONArray("results");
        boolean isFlagged = false;
        for (Object result : results) {
            JSONObject resultObject = (JSONObject) result;
            isFlagged = resultObject.getBoolean("flagged") || isFlagged;
        }
        return isFlagged;
    }

    private static boolean isMistralFlagged(JSONObject response) {
        boolean isFlagged = false;
        JSONArray results = response.getJSONArray("results");
        for (Object result : results) {
            JSONObject resultObject = (JSONObject) result;
            JSONObject categories = resultObject.getJSONObject("categories");
            for (String key : categories.keySet()) {
                if (categories.getBoolean(key)) {
                    isFlagged = true;
                    break;
                }
            }
        }
        return isFlagged;
    }

    public static String encodeImageToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

} 