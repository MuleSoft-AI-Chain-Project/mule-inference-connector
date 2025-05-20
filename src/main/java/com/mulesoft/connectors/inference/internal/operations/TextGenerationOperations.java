package com.mulesoft.connectors.inference.internal.operations;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import com.mulesoft.connectors.inference.internal.utils.ProviderUtils;
import com.mulesoft.connectors.inference.internal.utils.ResponseUtils;
import org.json.JSONArray;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

/**
 * This class contains operations for the inference connector.
 * Each public method represents an extension operation.
 */
public class TextGenerationOperations {
    private static final Logger logger = LoggerFactory.getLogger(TextGenerationOperations.class);
    private static final String ERROR_MSG_FORMAT = "%s result error";

    /**
     * Chat completions by messages array including system, users messages i.e. conversation history
     * @param messages the conversation history as a JSON array
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Chat-completions")
    @DisplayName("[Chat] Completions")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Native chat completion operation")
    public Result<InputStream, LLMResponseAttributes> chatCompletion(
            @Connection TextGenerationConnection connection, @Content InputStream messages)
            throws ModuleException {
        try {
            RequestPayloadHelper payloadHelper = connection.getRequestPayloadHelper();

            List<ChatPayloadRecord> messagesArray = payloadHelper.parseInputStreamToChatList(messages);

            URL chatCompUrl = new URL(connection.getApiURL());
            logger.debug("Chatting with {}", chatCompUrl);

            TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildPayload(connection, messagesArray,null);

            String response = ConnectionUtils.executeREST(chatCompUrl, connection, connection.getObjectMapper().writeValueAsString(requestPayloadDTO));

            logger.debug("Chat completions result {}", response);
            return ResponseUtils.processLLMResponse(response, connection);
        } catch (Exception e) {
            logger.error("Error in chat completions: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Chat completions"),
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }

    /**
     * Simple chat answer for a single prompt
     * @param prompt the user's prompt
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Chat-answer-prompt")
    @DisplayName("[Chat] Answer Prompt")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Simple chat answer prompt")
    public Result<InputStream, LLMResponseAttributes> chatAnswerPrompt(
            @Connection TextGenerationConnection connection, @Content String prompt) throws ModuleException {
        try {
            RequestPayloadHelper payloadHelper = connection.getRequestPayloadHelper();
            TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildChatAnswerPromptPayload(connection,prompt);

            URL chatCompUrl = new URL(connection.getApiURL());
            logger.debug("Chat answer prompt Url: {}", chatCompUrl);
            String response = ConnectionUtils.executeREST(chatCompUrl, connection, connection.getObjectMapper().writeValueAsString(requestPayloadDTO));

            logger.debug("Chat answer prompt result {}", response);

            return ResponseUtils.processLLMResponse(response, connection);
        } catch (Exception e) {
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Chat answer prompt"),
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }

    /**
     * Define a prompt template with instructions and data
     * @param connection LLM specific connector connection
     * @param template the template string
     * @param instructions instructions for the LLM
     * @param data the primary data content
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Agent-define-prompt-template")
    @DisplayName("[Agent] Define Prompt Template")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Define a prompt template with instructions, and data ")
    public Result<InputStream, LLMResponseAttributes> promptTemplate(
            @Connection TextGenerationConnection connection,
            @Content String template,
            @Content String instructions,
            @Content(primary = true) String data) throws ModuleException {
        try {

            TextGenerationRequestPayloadDTO requestPayloadDTO = connection
                    .getRequestPayloadHelper()
                    .buildPromptTemplatePayload(connection,template,instructions,data);
            logger.debug("payload sent to the LLM {}", requestPayloadDTO.toString());


            URL chatCompUrl = new URL(connection.getApiURL());
            String response = ConnectionUtils.executeREST(chatCompUrl, connection,
                    connection.getObjectMapper().writeValueAsString(requestPayloadDTO));

            logger.debug("Agent define prompt template result {}", response);
            return ResponseUtils.processLLMResponse(response, connection);
        } catch (Exception e) {
            logger.error("Error in agent define prompt template: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Agent define prompt template"),
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }
    /**
     * Define a tools template with instructions, data and tools
     * @param connection the connector connection
     * @param template the template string
     * @param instructions instructions for the LLM
     * @param data the primary data content
     * @param tools tools configuration as a JSON array
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Tools-native-template")
    @DisplayName("[Tools] Native Template (Reasoning only)")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Define a prompt template with instructions, data and tools")
    public Result<InputStream, LLMResponseAttributes> toolsTemplate(
            @Connection TextGenerationConnection connection,
            @Content String template,
            @Content String instructions,
            @Content(primary = true) String data,
            @Content @Summary("JSON Array defining the tools set to be used in the template so that the LLM can use them if required") InputStream tools) throws ModuleException {
    	try {
        	String payloadString = connection.getRequestPayloadHelper()
                    .buildToolsTemplatePayload(connection, template, instructions, data, tools);
            logger.debug("Payload sent to the LLM {}", payloadString);

            URL chatCompUrl = new URL(connection.getApiURL());
            String response = ConnectionUtils.executeREST(chatCompUrl, connection, payloadString);

            logger.debug("Tools use native template result {}", response);
            return ResponseUtils.processToolsResponse(response, connection);
        } catch (Exception e) {
            logger.error("Error in tools use native template: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "Tools use native template"),
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }

    /**
     * Define a tools template with instructions, data and tools
     * @param connection the connector connection
     * @param template the template string
     * @param instructions instructions for the LLM
     * @param data the primary data content
     * @return result containing the LLM response
     * @throws ModuleException if an error occurs during the operation
     */
    @MediaType(value = APPLICATION_JSON, strict = false)
    @Alias("Mcp-tools-native-template")
    @DisplayName("[MCP] Tooling")
    @OutputJsonType(schema = "api/response/Response.json")
    @Summary("Define a prompt template with instructions and data")
    public Result<InputStream, LLMResponseAttributes> mcpToolsTemplate(
            @Connection TextGenerationConnection connection,
            @Content String template,
            @Content String instructions,
            @Content(primary = true) String data) throws ModuleException {

        try {
            var tools = connection.getMcpHelper()
                    .getMcpToolsFromMultiple(connection);

            String payloadString = connection.getRequestPayloadHelper()
                    .buildToolsTemplatePayload(connection, template, instructions, data, tools);

            logger.debug("payload sent to the LLM {}", payloadString);

            URL chatCompUrl = new URL(connection.getApiURL());
            String response = ConnectionUtils.executeREST(chatCompUrl, connection, payloadString);

            logger.debug("MCP Tooling result {}", response);
            Result<InputStream, LLMResponseAttributes> apiResponse = ResponseUtils.processToolsResponse(response, connection);
            String apiResponseString = new String(apiResponse.getOutput().readAllBytes(), StandardCharsets.UTF_8);

            JSONArray toolExecutionResult = ProviderUtils.executeTools(connection.getMcpHelper().getMcpToolsArrayByServer(),
                    apiResponseString);

            return ResponseUtils.processToolsResponse(response, connection, toolExecutionResult);
        } catch (Exception e) {
            logger.error("Error in MCP Tooling: {}", e.getMessage(), e);
            throw new ModuleException(String.format(ERROR_MSG_FORMAT, "MCP Tooling"),
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }
}