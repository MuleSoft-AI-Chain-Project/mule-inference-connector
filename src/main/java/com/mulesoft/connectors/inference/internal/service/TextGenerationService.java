package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.ResponseWrapper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
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

  private final ObjectMapper objectMapper;

  public TextGenerationService(RequestPayloadHelper requestPayloadHelper, HttpRequestHelper httpRequestHelper,
                               HttpResponseHelper responseHelper, DefaultResponseMapper responseParser,
                               ObjectMapper objectMapper) {
    this.payloadHelper = requestPayloadHelper;
    this.httpRequestHelper = httpRequestHelper;
    this.responseHelper = responseHelper;
    this.responseParser = responseParser;
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

    TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.parseAndBuildChatCompletionPayload(connection, messages);

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

  private Result<InputStream, LLMResponseAttributes> executeToolsRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                          TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
  }

  private Result<InputStream, LLMResponseAttributes> executeChatRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                         TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    var response = executeChatRequest(connection, requestPayloadDTO);
    logger.debug("Response of chat REST request: {}", response);
    ResponseWrapper responseWrapper = responseHelper.processChatResponse(response, InferenceErrorType.CHAT_OPERATION_FAILURE);
    TextResponseDTO chatResponse = responseWrapper.getResponseDTO();
    


    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseParser.mapChatResponse(chatResponse)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse, connection.getModelName(), responseWrapper.getNativeResponse()));
  }

  private HttpResponse executeChatRequest(TextGenerationConnection connection,
                                             TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    logger.debug("Request payload: {} ", requestPayloadDTO);

    HttpResponse response = httpRequestHelper.executeChatRestRequest(connection,
                                                            connection.getApiURL(), requestPayloadDTO);

    return response;
  }
}
