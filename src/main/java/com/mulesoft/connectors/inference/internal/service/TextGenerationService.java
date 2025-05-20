package com.mulesoft.connectors.inference.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.TokenHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHandler;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TextGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(TextGenerationService.class);
    public static final String PAYLOAD_LOGGER_MSG = "Payload sent to the LLM {}";

    private final RequestPayloadHelper payloadHelper;
    private final HttpResponseHandler responseHandler;
    private final McpHelper mcpHelper;
    private final ObjectMapper objectMapper;

    public TextGenerationService(RequestPayloadHelper requestPayloadHelper, HttpResponseHandler responseHandler, McpHelper mcpHelper, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.responseHandler = responseHandler;
        this.mcpHelper = mcpHelper;
        this.objectMapper = objectMapper;
    }

    public Result<InputStream, LLMResponseAttributes> executeChatAnswerPrompt(TextGenerationConnection connection, String prompt) throws IOException, TimeoutException {

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildChatAnswerPromptPayload(connection,prompt);

        return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);

    }

    public Result<InputStream, LLMResponseAttributes> executeChatCompletion(TextGenerationConnection connection, InputStream messages) throws IOException, TimeoutException {

        List<ChatPayloadRecord> messagesArray = payloadHelper.parseInputStreamToChatList(messages);

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildPayload(connection, messagesArray, null);

        return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
    }

    public Result<InputStream, LLMResponseAttributes> definePromptTemplate(TextGenerationConnection connection, String template, String instructions, String data) throws IOException, TimeoutException {

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildPromptTemplatePayload(connection,template,instructions,data);
        logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO.toString());

        return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
    }

    public Result<InputStream, LLMResponseAttributes> executeToolsNativeTemplate(TextGenerationConnection connection,
                                                                                 String template, String instructions,
                                                                                 String data, InputStream tools)
            throws IOException, TimeoutException {

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
                .buildToolsTemplatePayload(connection, template, instructions, data, tools);

        logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO.toString());

        return executeToolsRequestAndFormatResponse(connection,requestPayloadDTO);
    }

    public Result<InputStream, LLMResponseAttributes> executeMcpTools(TextGenerationConnection connection, String template,
                                                                      String instructions, String data)
            throws IOException, TimeoutException {

        var tools = mcpHelper.getMcpToolsFromMultiple(connection);

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
                .buildToolsTemplatePayload(connection, template, instructions, data, tools);

        logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO.toString());

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);
        var chatRespOutput = chatResponse.choices().get(0);

        List<ToolResult> toolExecutionResult = mcpHelper.executeTools(mcpHelper.getMcpToolsArrayByServer(),
                chatRespOutput.message().toolCalls());

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(null,chatRespOutput.message().toolCalls(),toolExecutionResult)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespOutput.finishReason()));
    }

    private Result<InputStream, LLMResponseAttributes> executeToolsRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                            TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);
        var chatRespOutput = chatResponse.choices().get(0);

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(chatRespOutput.message().content(),chatRespOutput.message().toolCalls(),null)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespOutput.finishReason()));
    }

    private Result<InputStream, LLMResponseAttributes> executeChatRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                           TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);

        var chatRespOutput = chatResponse.choices().get(0);

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(chatRespOutput.message().content(),null,null)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespOutput.finishReason()));
    }

    private ChatCompletionResponse executeChatRequest(TextGenerationConnection connection, TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        var response = ConnectionUtils.executeChatRestRequest(connection,
                connection.getApiURL(), requestPayloadDTO);

        ChatCompletionResponse chatResponse = responseHandler.processChatResponse(response);
        logger.debug("Response of chat REST request: {}",chatResponse.toString());
        return chatResponse;
    }
}
