package com.mulesoft.connectors.internal.utils;

import com.mulesoft.connectors.internal.config.InferenceConfiguration;
import com.mulesoft.connectors.internal.constants.InferenceConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for payload operations.
 */
public class PayloadUtils {
    private static final String[] NO_TEMPERATURE_MODELS = {"o3-mini", "o1", "o1-mini"};

    /**
     * Build the payload for the API request
     * @param configuration the connector configuration
     * @param messagesArray the messages array
     * @param toolsArray the tools array (can be null)
     * @return the payload as a JSON object
     */
    public static JSONObject buildPayload(InferenceConfiguration configuration, JSONArray messagesArray, JSONArray toolsArray) {
        JSONObject payload = new JSONObject();
        if (!"AZURE_OPENAI".equals(configuration.getInferenceType())) {
            payload.put(InferenceConstants.MODEL, configuration.getModelName());
        }
        payload.put(InferenceConstants.MESSAGES, messagesArray);

        // Different max token parameter names for different providers
        if ("GROQ".equalsIgnoreCase(configuration.getInferenceType()) ||
                "OPENAI".equalsIgnoreCase(configuration.getInferenceType())) {
            payload.put(InferenceConstants.MAX_COMPLETION_TOKENS, configuration.getMaxTokens());
        } else {
            payload.put(InferenceConstants.MAX_TOKENS, configuration.getMaxTokens());
        }

        // Some models don't support temperature/top_p parameters
        String modelName = configuration.getModelName();
        if (!Arrays.asList(NO_TEMPERATURE_MODELS).contains(modelName)) {
            payload.put(InferenceConstants.TEMPERATURE, configuration.getTemperature());
            payload.put(InferenceConstants.TOP_P, configuration.getTopP());
        }

        // Add tools array if provided
        if (toolsArray != null && !toolsArray.isEmpty()) {
            payload.put(InferenceConstants.TOOLS, toolsArray);
        }

        // Special handling for Ollama's and Azure OpenAI's stream parameter
        if ("OLLAMA".equals(configuration.getInferenceType()) || "AZURE_OPENAI".equals(configuration.getInferenceType())) {
            payload.put("stream", false);
        }

        return payload;
    }
    
    /**
     * Build the payload for the Vertex AI API request
     * @param configuration the connector configuration
     * @param prompt the prompt
     * @param toolsArray the tools array (can be null)
     * @return the payload as a JSON object
     */
    public static JSONObject buildVertexAIPayload(InferenceConfiguration configuration, String prompt, 
    		JSONArray safetySettings, JSONObject systemInstruction, JSONArray tools) {
        JSONObject payload = new JSONObject();
        
        //create the parts of the contents
        JSONArray partsArray = new JSONArray();
        JSONObject usersPrompt = new JSONObject();
        usersPrompt.put("text", prompt);
        partsArray.put(usersPrompt);
        
        // Create the user role object
        JSONObject userContent = new JSONObject();
        userContent.put("role", "user");
        userContent.put("parts", partsArray);
        
        // Create the contents array and add the user content
        JSONArray contentsArray = new JSONArray();
        contentsArray.put(userContent);

        //add contents to the payload
        payload.put(InferenceConstants.CONTENTS, contentsArray);

        //add system instruction if provided
        if (systemInstruction != null) {
            payload.put(InferenceConstants.SYSTEM_INSTRUCTION, systemInstruction);
        }

        //create the generationConfig
        JSONObject generationConfig = new JSONObject();
        generationConfig.put("responseModalities", new String[]{"TEXT"});
        generationConfig.put("temperature", configuration.getTemperature());
        generationConfig.put("maxOutputTokens", configuration.getMaxTokens());
        generationConfig.put("topP", configuration.getTopP());
        
        //add generationConfig to the payload
        payload.put(InferenceConstants.GENERATION_CONFIG, generationConfig);

        //add safety settings if provided
        if (safetySettings != null && !safetySettings.isEmpty()) {
            payload.put(InferenceConstants.SAFETY_SETTINGS, safetySettings);
        }
        
        //add tools if provided
        if (tools != null && !tools.isEmpty()) {
            payload.put(InferenceConstants.TOOLS, tools);
        }

        return payload;
    }


    /**
     * Parse an input stream to a JSON array
     * @param inputStream the input stream to parse
     * @return the parsed JSON array
     * @throws IOException if an error occurs during parsing
     */
    public static JSONArray parseInputStreamToJsonArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new JSONArray();
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonString = stringBuilder.toString().trim();
            if (jsonString.isEmpty()) {
                return new JSONArray();
            }

            return new JSONArray(jsonString);
        }
    }

    /**
     * Creates a messages array with system prompt and user message
     * @param configuration the connector configuration
     * @param systemContent content for the system/assistant message
     * @param userContent content for the user message
     * @return JSONArray containing the messages
     */
    public static JSONArray createMessagesArrayWithSystemPrompt(
            InferenceConfiguration configuration, String systemContent, String userContent) {
        JSONArray messagesArray = new JSONArray();

        // Create system/assistant message based on provider
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", ProviderUtils.isAnthropic(configuration) ? "assistant" : "system");
        systemMessage.put("content", systemContent);
        messagesArray.put(systemMessage);

        // Create user message
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", userContent);
        messagesArray.put(userMessage);

        return messagesArray;
    }


    public static JSONArray createRequestImageURL(String provider, String prompt, String imageUrl) {

        if (provider.equalsIgnoreCase("ANTHROPIC")) {
            return createAnthropicImageURLRequest(prompt, imageUrl);
        } else {
            return createImageURLRequest(prompt, imageUrl);
        }
    }

    /**
     * Creates a messages array with system prompt and user message
     * @param prompt of the user
     * @param imageUrl of the image
     * @return JSONArray containing the messages
     */
    private static JSONArray createImageURLRequest(String prompt, String imageUrl) {
        JSONArray messagesArray = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        JSONArray contentArray = new JSONArray();

        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", prompt);
        contentArray.put(textContent);

        JSONObject imageContent = new JSONObject();
        imageContent.put("type", "image_url");
        JSONObject imageMessage = new JSONObject();
        imageMessage.put("url", imageUrl);
        imageContent.put("image_url", imageMessage);
        contentArray.put(imageContent);

        userMessage.put("content", contentArray);
        messagesArray.put(userMessage);


        return messagesArray;
    }


    /**
     * Creates a messages array with system prompt and user message
     * @param prompt of the user
     * @param imageUrl of the image
     * @return JSONArray containing the messages
     */
    private static JSONArray createAnthropicImageURLRequest(String prompt, String imageUrl) {
        JSONArray messagesArray = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");

        JSONArray contentArray = new JSONArray();

        JSONObject imageContent = new JSONObject();
        imageContent.put("type", "image");
        JSONObject imageSource = new JSONObject();
        imageSource.put("type", "url");
        imageSource.put("url", imageUrl);
        imageContent.put("source", imageSource);
        contentArray.put(imageContent);

        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", prompt);
        contentArray.put(textContent);

        userMessage.put("content", contentArray);
        messagesArray.put(userMessage);

        return messagesArray;
    }

}