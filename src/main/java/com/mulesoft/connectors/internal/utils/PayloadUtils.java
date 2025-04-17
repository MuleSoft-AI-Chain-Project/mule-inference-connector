package com.mulesoft.connectors.internal.utils;

import com.mulesoft.connectors.internal.config.TextGenerationConfig;
import com.mulesoft.connectors.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.internal.connection.ModerationImageGenerationBase;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for payload operations.
 */
public class PayloadUtils {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadUtils.class);


    private static final String[] NO_TEMPERATURE_MODELS = {"o3-mini","o3","o4-mini","o4", "o1", "o1-mini"};

    /**
     * Build the payload for the API request
     * @param configuration the connector configuration
     * @param messagesArray the messages array
     * @param toolsArray the tools array (can be null)
     * @return the payload as a JSON object
     */
    public static JSONObject buildPayload(ChatCompletionBase configuration, JSONArray messagesArray, JSONArray toolsArray) {
        JSONObject payload = new JSONObject();
        
        String inferenceType = configuration.getInferenceType();
    	
    	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());
    	
    	LOGGER.debug("provider {} inferenceType {}", provider, inferenceType);


    	if ("Google".equalsIgnoreCase(provider)) {
			//add contents to the payload
        
	        payload.put(InferenceConstants.CONTENTS, messagesArray);
	        
	        JSONObject generationConfig = buildVertexAIGenerationConfig(configuration);
	        
	        payload.put(InferenceConstants.GENERATION_CONFIG, generationConfig);

		} else {
			
			if ("Anthropic".equalsIgnoreCase(provider)) {
				payload.put(InferenceConstants.VERTEX_AI_ANTHROPIC_VERSION, InferenceConstants.VERTEX_AI_ANTHROPIC_VERSION_VALUE);
			} 
			
			if (!"AZURE_OPENAI".equalsIgnoreCase(inferenceType) &&
				    !"IBM_WATSON".equalsIgnoreCase(inferenceType) &&
				    !"Anthropic".equalsIgnoreCase(provider)) {
				    //set the model only if:
					//The inference type is not "AZURE_OPENAI" and
					//The inference type is not "IBM_WATSON" and
					//The provider is not "Anthropic"
				    payload.put(InferenceConstants.MODEL, configuration.getModelName());
			}

			if ("IBM_WATSON".equals(configuration.getInferenceType())) {
                payload.put("model_id", configuration.getModelName());
                payload.put("project_id", configuration.getibmWatsonProjectID());
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
	        if ("OLLAMA".equals(configuration.getInferenceType()) || "AZURE_OPENAI".equals(configuration.getInferenceType()) || "Meta".equalsIgnoreCase(provider)) {
	            payload.put("stream", false);
	        }
		}
		
        return payload;
    }

    /**
     * Build the payload for the API request
     * @param connection the connector configuration
     * @param requestJson the payload with prompt
     * @return the payload as a JSON object
     */
    public static JSONObject buildPayloadImageGeneration(ModerationImageGenerationBase connection, JSONObject requestJson) {
        JSONObject payload = requestJson;

        if (("OPENAI".equalsIgnoreCase(connection.getInferenceType()))
            || ("XAI".equalsIgnoreCase(connection.getInferenceType()))){
            payload.put("model", connection.getModelName());
        }

        return payload;
    }
    
    /**
     * Build the payload for the Vertex AI API request
     * @param configuration the connector configuration
     * @param prompt the prompt
     * @return the payload as a JSON object
     */
    public static JSONObject buildVertexAIPayload(ChatCompletionBase configuration, String prompt,
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
        JSONObject generationConfig = buildVertexAIGenerationConfig(configuration);	        
        
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
            ChatCompletionBase configuration, String systemContent, String userContent) {
        JSONArray messagesArray = new JSONArray();

        // Create system/assistant message based on provider
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "ANTHROPIC".equals(configuration.getInferenceType()) ? "assistant" : "system");
        systemMessage.put("content", systemContent);
        messagesArray.put(systemMessage);

        // Create user message
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", userContent);
        messagesArray.put(userMessage);

        return messagesArray;
    }


    public static JSONArray createRequestImageURL(String provider, String prompt, String imageUrl) throws IOException {

        if (provider.equalsIgnoreCase("ANTHROPIC")) {
            return createAnthropicImageURLRequest(prompt, imageUrl);
        } else if (provider.equalsIgnoreCase("OLLAMA")) {
            return createOllamaImageURLRequest(prompt, imageUrl);
        } else if ("VERTEX_AI".equalsIgnoreCase(provider) || "VERTEX_AI_EXPRESS".equalsIgnoreCase(provider)) {
            return createVertexAIImageURLRequest(prompt, imageUrl);
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
    private static JSONArray createImageURLRequest(String prompt, String imageUrl) throws IOException {
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
        if (isBase64String(imageUrl)) {
            imageMessage.put("url", "data:" + getMimeType(imageUrl) + ";base64," + imageUrl);
        } else{
            imageMessage.put("url", imageUrl);
        }
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
    private static JSONArray createOllamaImageURLRequest(String prompt, String imageUrl) throws IOException {
        // Create messages array
        JSONArray messagesArray = new JSONArray();

        // Create user message object
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        // Create images array
        JSONArray imagesArray = new JSONArray();

        // Add the image (either base64 or URL)
        if (isBase64String(imageUrl)) {
            // For base64 images, no need to add data: prefix as it's handled directly
            imagesArray.put(imageUrl);
        } else {
            // For URL images
            imagesArray.put(imageUrl);
        }

        // Add images array to user message
        userMessage.put("images", imagesArray);

        // Add the message to the array
        messagesArray.put(userMessage);

        return messagesArray;
    }


    /**
     * Creates a messages array with system prompt and user message
     * @param prompt of the user
     * @param imageUrl of the image
     * @return JSONArray containing the messages
     */
    private static JSONArray createAnthropicImageURLRequest(String prompt, String imageUrl) throws IOException {
        JSONArray messagesArray = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");

        JSONArray contentArray = new JSONArray();

        JSONObject imageContent = new JSONObject();
        imageContent.put("type", "image");
        JSONObject imageSource = new JSONObject();
        if (isBase64String(imageUrl)) {
            imageSource.put("type", "base64");
            imageSource.put("media_type", getMimeType(imageUrl));
            imageSource.put("data", imageUrl);
        } else{
            imageSource.put("type", "url");
            imageSource.put("url", imageUrl);
        }

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
    
    /**
     * Creates a messages array with system prompt and base64 contents or URL of the image for Vertex AI Express
     * @param prompt of the user
     * @param imageUrl of the image
     * @return JSONArray containing the messages
     */
    private static JSONArray createVertexAIImageURLRequest(String prompt, String imageUrl) throws IOException {

    	JSONArray parts = new JSONArray();
    	
        if (isBase64String(imageUrl)) {
            JSONObject inlineData = new JSONObject();
            inlineData.put("mimeType", getMimeType(imageUrl));
            inlineData.put("data", imageUrl);  // imageUrl contains base64 in this case

            JSONObject inlineDataWrapper = new JSONObject();
            inlineDataWrapper.put("inlineData", inlineData);

            parts.put(inlineDataWrapper);
        } else {
            JSONObject fileData = new JSONObject();
            fileData.put("mimeType", getMimeTypeFromUrl(imageUrl));
            fileData.put("fileUri", imageUrl);  // imageUrl contains gs:// URI in this case

            JSONObject fileDataWrapper = new JSONObject();
            fileDataWrapper.put("fileData", fileData);

            parts.put(fileDataWrapper);
        }

        // Add text part
        JSONObject textPart = new JSONObject();
        textPart.put("text", prompt);

        parts.put(textPart);

        // Add to contents array
        JSONObject content = new JSONObject();
        content.put("role", "user");
        content.put("parts", parts);

        JSONArray contents = new JSONArray();
        contents.put(content);

        return contents;
    
    }


    
    /**
     * Build the payload for the chatAnswerPrompt request
     * @param configuration The connector configuration
     * @param prompt The prompt
     * @return The payload as a JSON object
     */
    public static JSONObject buildChatAnswerPromptPayload(TextGenerationConfig inferenceConfig, ChatCompletionBase configuration, String prompt) {
    	JSONObject payload;
    	
    	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

    	if ("Google".equalsIgnoreCase(provider)) {
   	    	JSONArray safetySettings = new JSONArray(); // Empty array
	    	JSONObject systemInstruction = new JSONObject(); // Empty object
	    	JSONArray tools = new JSONArray(); // Empty array
			payload = PayloadUtils.buildVertexAIPayload(configuration, prompt, safetySettings, systemInstruction, tools);
		} else if ("Anthropic".equalsIgnoreCase(provider)) {
			//for ANthropic
			
			JSONObject textObject = new JSONObject()
		            .put("type", "text")
		            .put("text", prompt);

		    JSONArray contentArray = new JSONArray()
		            .put(textObject);

		    JSONObject messageObject = new JSONObject()
		            .put("role", "user")
		            .put("content", contentArray);

		    JSONArray messagesArray = new JSONArray()
		            .put(messageObject);
	        payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
		
		} else {
	        JSONArray messagesArray = new JSONArray();
	        JSONObject usersPrompt = new JSONObject();
	        usersPrompt.put("role", "user");
	        usersPrompt.put("content", prompt);
	        messagesArray.put(usersPrompt);
	        payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
		}
	
		return payload;
    }
    
    /**
     * Build the payload for the promptTemplate request
     * @param configuration The connector configuration
     * @param template The template string
     * @param instructions The instructions string
     * @param data The primary data content
     * @return The payload as a JSON object
     */
    public static JSONObject buildPromptTemplatePayload(TextGenerationConfig inferenceConfig,ChatCompletionBase configuration, String template, String instructions, String data) {
    	JSONObject payload;
    	
       	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

       	if ("Google".equalsIgnoreCase(provider)) {
    		//for google/gemini
    		//Create systemInstruction object
	    	//Step 1: Wrap text in a part object
	        JSONObject part = new JSONObject();
	        part.put("text", template + " - " + instructions);
	
	        //Step 2: Create parts array
	        JSONArray parts = new JSONArray();
	        parts.put(part);
	
	        //Step 3: Create systemInstruction object
	        JSONObject systemInstruction = new JSONObject();
	        systemInstruction.put("parts", parts);
	    	
	    	JSONArray safetySettings = new JSONArray(); // Empty array

	    	JSONArray tools = new JSONArray(); // Empty array

	    	payload = PayloadUtils.buildVertexAIPayload(configuration, data, safetySettings, systemInstruction, tools);
		} else if ("Anthropic".equalsIgnoreCase(provider)) {
			//for Anthropic thru Vertex AI
			
			JSONObject textObject = new JSONObject()
		            .put("type", "text")
		            .put("text", data);

		    JSONArray contentArray = new JSONArray()
		            .put(textObject);

		    JSONObject messageObject = new JSONObject()
		            .put("role", "user")
		            .put("content", contentArray);

		    JSONArray messagesArray = new JSONArray()
		            .put(messageObject);
	        payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
	        
	        //add the template+instructions to "system" field
	        payload.put(InferenceConstants.SYSTEM, template + " - " + instructions);	    
	        
		
		} else {
	        JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
	                configuration, template + " - " + instructions, data);
	
	        payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
	
		}

		return payload;

    }

    /**
     * Build the payload for the toolsTemplate request
     * @param configuration The connector configuration
     * @param template The template string
     * @param instructions The instructions string
     * @param data The primary data content
     * @param tools The tools set to be used
     * @return The payload as a JSON object
     */
    public static JSONObject buildToolsTemplatePayload(TextGenerationConfig inferenceConfig, ChatCompletionBase configuration, String template,
                                                       String instructions, String data, InputStream tools) throws IOException {

        	JSONObject payload;

        	String type = configuration.getInferenceType();
        	
           	String provider = ProviderUtils.getProviderByModel(configuration.getModelName());

           	JSONArray toolsArray = PayloadUtils.parseInputStreamToJsonArray(tools); 
           	
           	LOGGER.debug("provider: {} toolsArray: {}", provider, toolsArray.toString());

        	if ("Google".equalsIgnoreCase(provider)) {
            	
            	//Create systemInstruction object
            	//Step 1: Wrap text in a part object
                JSONObject part = new JSONObject();
                part.put("text", template + " - " + instructions);

                //Step 2: Create parts array
                JSONArray parts = new JSONArray();
                parts.put(part);

                //Step 3: Create systemInstruction object
                JSONObject systemInstruction = new JSONObject();
                systemInstruction.put("parts", parts);
            	

            	JSONArray safetySettings = new JSONArray(); // Empty array

            	payload = PayloadUtils.buildVertexAIPayload(configuration, data, safetySettings, systemInstruction, toolsArray);
        	} else if ("Anthropic".equalsIgnoreCase(provider)) {
    			//for Anthropic thru Vertex AI
        		//Support for custom tools is planned, but not yet fully available or documented across all Claude models and endpoints. 
        		//As of now (April 2025), Claude 3 models via Vertex AI reject tools[].custom despite the field appearing valid.
        		
            	//the code below can be used when the support is available
            	
        		JSONObject textObject = new JSONObject()
    		            .put("type", "text")
    		            .put("text", data);

    		    JSONArray contentArray = new JSONArray()
    		            .put(textObject);

    		    JSONObject messageObject = new JSONObject()
    		            .put("role", "user")
    		            .put("content", contentArray);

    		    JSONArray messagesArray = new JSONArray()
    		            .put(messageObject);
    	        payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
    	        
    	        //add the template+instructions to "system" field
    	        payload.put(InferenceConstants.SYSTEM, template + " - " + instructions);
    	        payload = PayloadUtils.buildPayload(configuration, messagesArray, toolsArray);
    	        
        	
        	} else {
        	
        	    //As of April 2025, Meta LLaMA models on Vertex AI (including meta/llama-4-maverick-17b-128e-instruct-maas) do not support tools or function calling 
        		//via the OpenAI-compatible /chat/completions endpoint or the text generation endpoint.
        		
        		
        		JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
        				configuration, template + " - " + instructions, data);
        		payload = PayloadUtils.buildPayload(configuration, messagesArray, toolsArray);
            }
    
    		return payload;
    }

    public static boolean isBase64String(String str) {
        if (str == null || str.length() % 4 != 0 || !str.matches("^[A-Za-z0-9+/]*={0,2}$")) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String getMimeType(String base64String) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        return mimeType != null ? mimeType : "image/jpeg";
    }
    
    public static String getMimeTypeFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return "image/jpeg"; // Default
        }

        imageUrl = imageUrl.toLowerCase().trim();

        if (imageUrl.endsWith(".png")) {
            return "image/png";
        } else if (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".jpeg")) {
            return "image/jpeg";
        }

        // Add more types if needed
        return "image/jpeg"; // Default fallback
    }
    
    public static JSONObject buildVertexAIGenerationConfig(ChatCompletionBase configuration) {
	    //create the generationConfig
	    JSONObject generationConfig = new JSONObject();
	    generationConfig.put("responseModalities", new String[]{"TEXT"});
	    generationConfig.put("temperature", configuration.getTemperature());
	    generationConfig.put("maxOutputTokens", configuration.getMaxTokens());
	    generationConfig.put("topP", configuration.getTopP());
        
	    return generationConfig;
    }


    public static JSONObject createRequestImageGeneration(String provider, String prompt) throws IOException {

        if (provider.equalsIgnoreCase("OPENAI")) {
            return createImageGenerationRequestOpenaI(prompt);
        } else if(provider.equalsIgnoreCase("HUGGING_FACE")) {
            return createImageGenerationRequestHuggingface(prompt);
        } else if (provider.equalsIgnoreCase("STABILITY_AI")) {
            return createImageGenerationRequestStabilityAI(prompt);
        } else {
            return createImageGenerationRequestOpenaI(prompt);
        }
    }

    /**
     * Creates a messages array with system prompt and user message
     * @param prompt of the user
     * @return JSONArray containing the messages
     */
    private static JSONObject createImageGenerationRequestOpenaI(String prompt) throws IOException {

        JSONObject requestPayload = new JSONObject();
        requestPayload.put("prompt", prompt);
        requestPayload.put("response_format", "b64_json");

        return requestPayload;
    }

    /**
     * Creates a messages array with system prompt and user message
     * @param prompt of the user
     * @return JSONArray containing the messages
     */
    private static JSONObject createImageGenerationRequestHuggingface(String prompt) throws IOException {

        JSONObject requestPayload = new JSONObject();
        requestPayload.put("inputs", prompt);
        return requestPayload;
    }

    private static JSONObject createImageGenerationRequestStabilityAI(String prompt) throws IOException {
        JSONObject requestPayload = new JSONObject();
        requestPayload.put("prompt", prompt);
        return requestPayload;
    }



}
