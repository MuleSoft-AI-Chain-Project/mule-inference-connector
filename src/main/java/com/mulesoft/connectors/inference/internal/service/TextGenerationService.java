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
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.TokenHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class TextGenerationService implements BaseService{

    private static final Logger logger = LoggerFactory.getLogger(TextGenerationService.class);
    public static final String PAYLOAD_LOGGER_MSG = "Payload sent to the LLM {}";

    private final RequestPayloadHelper payloadHelper;
    private final HttpRequestHelper httpRequestHelper;
    private final HttpResponseHelper responseHandler;
    private final McpHelper mcpHelper;
    private final ObjectMapper objectMapper;

    public TextGenerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper, HttpResponseHelper responseHandler, McpHelper mcpHelper, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.httpRequestHelper = httpRequestHelper;
        this.responseHandler = responseHandler;
        this.mcpHelper = mcpHelper;
        this.objectMapper = objectMapper;
    }

    public Result<InputStream, LLMResponseAttributes> executeChatAnswerPrompt(TextGenerationConnection connection, String prompt) throws IOException, TimeoutException {

        return executeChatRequestAndFormatResponse(connection,
                payloadHelper.buildChatAnswerPromptPayload(connection,prompt));
    }

    public Result<InputStream, LLMResponseAttributes> executeChatCompletion(TextGenerationConnection connection, InputStream messages) throws IOException, TimeoutException {

        List<ChatPayloadRecord> messagesArray = payloadHelper.parseInputStreamToChatList(messages);

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildPayload(connection, messagesArray, null);

        return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
    }

    public Result<InputStream, LLMResponseAttributes> definePromptTemplate(TextGenerationConnection connection, String template, String instructions, String data) throws IOException, TimeoutException {

        return executeChatRequestAndFormatResponse(connection,
                payloadHelper.buildPromptTemplatePayload(connection,template,instructions,data));
    }

    public Result<InputStream, LLMResponseAttributes> executeToolsNativeTemplate(TextGenerationConnection connection,
                                                                                 String template, String instructions,
                                                                                 String data, InputStream tools)
            throws IOException, TimeoutException {

        return executeToolsRequestAndFormatResponse(connection,payloadHelper
                .buildToolsTemplatePayload(connection, template, instructions, data, tools));
    }

    public Result<InputStream, LLMResponseAttributes> executeMcpTools(TextGenerationConnection connection, String template,
                                                                      String instructions, String data)
            throws IOException, TimeoutException {

        var tools = mcpHelper.getMcpToolsFromMultiple(connection);

        TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
                .buildToolsTemplatePayload(connection, template, instructions, data, tools);

        logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO);

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);
        var chatRespFirstChoice = chatResponse.choices().get(0);

        List<ToolResult> toolExecutionResult = mcpHelper.executeTools(mcpHelper.getMcpToolsArrayByServer(),
                chatRespFirstChoice.message().toolCalls());

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(null,chatRespFirstChoice.message().toolCalls(),toolExecutionResult)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespFirstChoice.finishReason()));
    }

    private Result<InputStream, LLMResponseAttributes> executeToolsRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                            TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);
        var chatRespFirstChoice = chatResponse.choices().get(0);

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(chatRespFirstChoice.message().content(),
                        chatRespFirstChoice.message().toolCalls(),null)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespFirstChoice.finishReason()));
    }

    private Result<InputStream, LLMResponseAttributes> executeChatRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                           TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        ChatCompletionResponse chatResponse = executeChatRequest(connection, requestPayloadDTO);

        var chatRespFirstChoice = chatResponse.choices().get(0);

        return ResponseHelper.createLLMResponse(
                objectMapper.writeValueAsString(new TextGenerationResponse(chatRespFirstChoice.message().content(),null,null)),
                TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespFirstChoice.finishReason()));
    }

    private ChatCompletionResponse executeChatRequest(TextGenerationConnection connection, TextGenerationRequestPayloadDTO requestPayloadDTO)
            throws IOException, TimeoutException {

        logger.debug("Request payload: {} ", requestPayloadDTO);

        var response = httpRequestHelper.executeChatRestRequest(connection,
                connection.getApiURL(), requestPayloadDTO);

        ChatCompletionResponse chatResponse = responseHandler.processChatResponse(response, InferenceErrorType.CHAT_OPERATION_FAILURE);
        logger.debug("Response of chat REST request: {}",chatResponse);
        return chatResponse;
    }
}
