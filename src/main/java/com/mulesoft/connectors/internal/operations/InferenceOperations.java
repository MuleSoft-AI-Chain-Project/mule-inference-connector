package com.mulesoft.connectors.internal.operations;

import com.mulesoft.connectors.internal.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.internal.api.metadata.TokenUsage;
import com.mulesoft.connectors.internal.config.InferenceConfiguration;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import com.mulesoft.connectors.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.internal.helpers.TokenHelper;
import com.mulesoft.connectors.internal.utils.ConnectionUtils;
import com.mulesoft.connectors.internal.utils.PayloadUtils;
import com.mulesoft.connectors.internal.utils.ProviderUtils;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mulesoft.connectors.internal.helpers.ResponseHelper.createLLMResponse;
import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class InferenceOperations {
    private static final Logger LOGGER = LoggerFactory.getLogger(InferenceOperations.class);
    private static final String[] NO_TEMPERATURE_MODELS = {"o3-mini", "o1", "o1-mini"};
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
            JSONArray messagesArray = new JSONArray();
            JSONObject usersPrompt = new JSONObject();
            usersPrompt.put("role", "user");
            usersPrompt.put("content", prompt);
            messagesArray.put(usersPrompt);

            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            JSONObject payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
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
            JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
                    configuration, template + " - " + instructions, data);

            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            JSONObject payload = PayloadUtils.buildPayload(configuration, messagesArray, null);
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
            JSONArray toolsArray = PayloadUtils.parseInputStreamToJsonArray(tools);
            JSONArray messagesArray = PayloadUtils.createMessagesArrayWithSystemPrompt(
                    configuration, template + " - " + instructions, data);

            URL chatCompUrl = ConnectionUtils.getConnectionURLChatCompletion(configuration);
            JSONObject payload = PayloadUtils.buildPayload(configuration, messagesArray, toolsArray);
            String response = ConnectionUtils.executeREST(chatCompUrl, configuration, payload.toString());

            LOGGER.debug("Tools use native template result {}", response);
            return ResponseUtils.processToolsResponse(response, configuration);
        } catch (Exception e) {
            LOGGER.error("Error in tools use native template: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Tools use native template"),
                    InferenceErrorType.CHAT_COMPLETION, e);
        }
    }

    /**
     * Creates a messages array with system prompt and user message
     * @param configuration the connector configuration
     * @param systemContent content for the system/assistant message
     * @param userContent content for the user message
     * @return JSONArray containing the messages
     */
    private JSONArray createMessagesArrayWithSystemPrompt(
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

    /**
     * Check if the inference type is OLLAMA
     * @param configuration the connector configuration
     * @return true if the inference type is OLLAMA, false otherwise
     */
    private boolean isOllama(InferenceConfiguration configuration) {
        return "OLLAMA".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is Anthropic
     * @param configuration the connector configuration
     * @return true if the inference type is Anthropic, false otherwise
     */
    private boolean isAnthropic(InferenceConfiguration configuration) {
        return "ANTHROPIC".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is NVIDIA
     * @param configuration the connector configuration
     * @return true if the inference type is NVIDIA, false otherwise
     */
    private boolean isNvidia(InferenceConfiguration configuration) {
        return "NVIDIA".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is Cohere
     * @param configuration the connector configuration
     * @return true if the inference type is Cohere, false otherwise
     */
    private boolean isCohere(InferenceConfiguration configuration) {
        return "COHERE".equals(configuration.getInferenceType());
    }
}