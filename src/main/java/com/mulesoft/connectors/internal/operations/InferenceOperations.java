package com.mulesoft.connectors.internal.operations;

import com.mulesoft.connectors.internal.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.internal.config.InferenceConfiguration;
import com.mulesoft.connectors.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.internal.utils.ConnectionUtils;
import com.mulesoft.connectors.internal.utils.PayloadUtils;
import com.mulesoft.connectors.internal.utils.ResponseUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class InferenceOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(InferenceOperations.class);
    private static final String ERROR_MSG_FORMAT = "%s result error";

    /**
     * Chat completions by messages array including system, users messages i.e. conversation history
     * @param configuration the connector configuration
     * @param messages the conversation history as a JSON array
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Chat-completions")
    @OutputJsonType(schema = "api/response/Response.json")
    public Result<InputStream, LLMResponseAttributes> chatCompletion(
            @Config InferenceConfiguration configuration,
            @Content InputStream messages) throws ModuleException {
        try {
            JSONArray messagesArray = PayloadUtils.parseInputStreamToJsonArray(messages);
            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);

            LOGGER.debug("Chatting with {}", chatCompUrl);
            
            JSONObject payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
            String response = ConnectionUtils.executeREST(chatCompUrl, configuration, payload.toString());

            LOGGER.debug("Chat completions result {}", response);
            return ResponseUtils.processLLMResponse(response, configuration);
        } catch (Exception e) {
            LOGGER.error("Error in chat completions: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Chat completions"),
                    InferenceErrorType.CHAT_COMPLETION, e);
        }
    }

    /**
     * Simple chat answer for a single prompt
     * @param configuration the connector configuration
     * @param prompt the user's prompt
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Chat-answer-prompt")
    @OutputJsonType(schema = "api/response/Response.json")
    public Result<InputStream, LLMResponseAttributes> chatAnswerPrompt(
            @Config InferenceConfiguration configuration,
            @Content String prompt) throws ModuleException {
        try {
        	
        	JSONObject payload;

        	
        	if ("VERTEX_AI_EXPRESS".equalsIgnoreCase(configuration.getInferenceType())) {
            	JSONArray safetySettings = new JSONArray(); // Empty array
            	JSONObject systemInstruction = new JSONObject(); // Empty object
            	JSONArray tools = new JSONArray(); // Empty array
        		payload = PayloadUtils.buildVertexAIPayload(configuration, prompt, safetySettings, systemInstruction, tools);
        	} else {
	            JSONArray messagesArray = new JSONArray();
	            JSONObject usersPrompt = new JSONObject();
	            usersPrompt.put("role", "user");
	            usersPrompt.put("content", prompt);
	            messagesArray.put(usersPrompt);
	            payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
        	}
        	
            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            String response = ConnectionUtils.executeREST(chatCompUrl, configuration, payload.toString());

            LOGGER.debug("Chat answer prompt result {}", response);
            return ResponseUtils.processLLMResponse(response, configuration);
        } catch (Exception e) {
            LOGGER.error("Error in chat answer prompt: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Chat answer prompt"),
                    InferenceErrorType.CHAT_COMPLETION, e);
        }
    }

    /**
     * Define a prompt template with instructions and data
     * @param configuration the connector configuration
     * @param template the template string
     * @param instructions instructions for the LLM
     * @param data the primary data content
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Agent-define-prompt-template")
    @OutputJsonType(schema = "api/response/Response.json")
    public Result<InputStream, LLMResponseAttributes> promptTemplate(
            @Config InferenceConfiguration configuration,
            @Content String template,
            @Content String instructions,
            @Content(primary = true) String data) throws ModuleException {
        try {
        	
        	JSONObject payload;

        	
        	if ("VERTEX_AI_EXPRESS".equalsIgnoreCase(configuration.getInferenceType())) {
            	JSONArray safetySettings = new JSONArray(); // Empty array
            	
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
            	
            	JSONArray tools = new JSONArray(); // Empty array
        		payload = PayloadUtils.buildVertexAIPayload(configuration, data, safetySettings, systemInstruction, tools);
        	} else {
                JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
                        configuration, template + " - " + instructions, data);

                payload = PayloadUtils.buildPayload(configuration, messagesArray, null);

        	}
        	

            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            String response = ConnectionUtils.executeREST(chatCompUrl, configuration, payload.toString());

            LOGGER.debug("Agent define prompt template result {}", response);
            return ResponseUtils.processLLMResponse(response, configuration);
        } catch (Exception e) {
            LOGGER.error("Error in agent define prompt template: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Agent define prompt template"),
                    InferenceErrorType.CHAT_COMPLETION, e);
        }
    }

    /**
     * Define a tools template with instructions, data and tools
     * @param configuration the connector configuration
     * @param template the template string
     * @param instructions instructions for the LLM
     * @param data the primary data content
     * @param tools tools configuration as a JSON array
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Tools-native-template")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Define a prompt template with instructions, data and tools")
    public Result<InputStream, LLMResponseAttributes> toolsTemplate(
            @Config InferenceConfiguration configuration,
            @Content String template,
            @Content String instructions,
            @Content(primary = true) String data,
            @Content @Summary("JSON Array defining the tools set to be used in the template so that the LLM can use them if required") InputStream tools) throws ModuleException {
        try {
        	
        	
        	JSONObject payload;

        	
        	if ("VERTEX_AI_EXPRESS".equalsIgnoreCase(configuration.getInferenceType())) {
            	JSONArray safetySettings = new JSONArray(); // Empty array
            	
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
            	
            	JSONArray toolsArray = PayloadUtils.parseInputStreamToJsonArray(tools); 
        		payload = PayloadUtils.buildVertexAIPayload(configuration, data, safetySettings, systemInstruction, toolsArray);
        	} else {
        	
        	
        		JSONArray toolsArray = PayloadUtils.parseInputStreamToJsonArray(tools);
        		JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
        				configuration, template + " - " + instructions, data);
        		payload = PayloadUtils.buildPayload(configuration, messagesArray, toolsArray);
            }
        	
            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            String response = ConnectionUtils.executeREST(chatCompUrl, configuration, payload.toString());

            LOGGER.debug("Tools use native template result {}", response);
            return ResponseUtils.processToolsResponse(response, configuration);
        } catch (Exception e) {
            LOGGER.error("Error in tools use native template: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Tools use native template"),
                    InferenceErrorType.CHAT_COMPLETION, e);
        }
    }

}