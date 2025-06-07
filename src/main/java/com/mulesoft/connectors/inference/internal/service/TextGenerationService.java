package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.request.ChatPayloadRecord;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextGenerationService implements BaseService {

  private static final Logger logger = LoggerFactory.getLogger(TextGenerationService.class);
  public static final String PAYLOAD_LOGGER_MSG = "Payload sent to the LLM {}";

  private final RequestPayloadHelper payloadHelper;
  private final HttpRequestHelper httpRequestHelper;
  private final HttpResponseHelper responseHelper;
  private final DefaultResponseMapper responseParser;
  private final McpHelper mcpHelper;
  private final ObjectMapper objectMapper;

  public TextGenerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                               HttpResponseHelper responseHelper, DefaultResponseMapper responseParser, McpHelper mcpHelper,
                               ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHelper = responseHelper;
    this.responseParser = responseParser;
    this.mcpHelper = mcpHelper;
    this.objectMapper = objectMapper;
  }

  public Result<InputStream, LLMResponseAttributes> executeChatAnswerPrompt(TextGenerationConnection connection, String prompt)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection,
                                               payloadHelper.buildChatAnswerPromptPayload(connection, prompt));
  }

  public Result<InputStream, LLMResponseAttributes> executeChatCompletion(TextGenerationConnection connection,
                                                                          InputStream messages)
      throws IOException, TimeoutException {

    List<ChatPayloadRecord> messagesArray = payloadHelper.parseInputStreamToChatList(messages);

    TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildPayload(connection, messagesArray, null);

    return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
  }

  public Result<InputStream, LLMResponseAttributes> definePromptTemplate(TextGenerationConnection connection, String template,
                                                                         String instructions, String data)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection,
                                               payloadHelper.buildPromptTemplatePayload(connection, template, instructions,
                                                                                        data));
  }

  public Result<InputStream, LLMResponseAttributes> executeToolsNativeTemplate(TextGenerationConnection connection,
                                                                               String template, String instructions,
                                                                               String data, InputStream tools)
      throws IOException, TimeoutException {

    return executeToolsRequestAndFormatResponse(connection, payloadHelper
        .buildToolsTemplatePayload(connection, template, instructions, data, tools));
  }

  public Result<InputStream, LLMResponseAttributes> executeMcpTools(TextGenerationConnection connection, String template,
                                                                    String instructions, String data)
      throws IOException, TimeoutException {

    var tools = mcpHelper.getMcpToolsFromMultiple(connection);

    TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
        .buildToolsTemplatePayload(connection, template, instructions, data, tools);

    logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO);

    TextResponseDTO chatResponse = executeChatRequest(connection, requestPayloadDTO);

    List<ToolResult> toolExecutionResult = mcpHelper.executeTools(mcpHelper.getMcpToolsArrayByServer(),
                                                                  responseParser.mapToolCalls(chatResponse),
                                                                  connection.getTimeout());

    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseParser
                                                .mapMcpExecuteToolsResponse(chatResponse, toolExecutionResult)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse));
  }

  private Result<InputStream, LLMResponseAttributes> executeToolsRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                          TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    TextResponseDTO chatResponse = executeChatRequest(connection, requestPayloadDTO);

    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseParser.mapChatResponse(chatResponse)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse));
  }

  private Result<InputStream, LLMResponseAttributes> executeChatRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                         TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    TextResponseDTO chatResponse = executeChatRequest(connection, requestPayloadDTO);

    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseParser.mapChatResponse(chatResponse)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse));
  }

  private TextResponseDTO executeChatRequest(TextGenerationConnection connection,
                                             TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    logger.debug("Request payload: {} ", requestPayloadDTO);

    var response = httpRequestHelper.executeChatRestRequest(connection,
                                                            connection.getApiURL(), requestPayloadDTO);

    TextResponseDTO chatResponse =
        responseHelper.processChatResponse(response, InferenceErrorType.CHAT_OPERATION_FAILURE);
    logger.debug("Response of chat REST request: {}", chatResponse);
    return chatResponse;
  }
}
