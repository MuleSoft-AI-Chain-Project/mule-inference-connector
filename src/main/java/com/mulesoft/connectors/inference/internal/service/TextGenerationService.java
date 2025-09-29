package com.mulesoft.connectors.inference.internal.service;

import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.runtime.extension.api.client.ExtensionsClient;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.mcp.McpConfig;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TextResponseDTO;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.mcp.McpHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.helpers.request.HttpRequestHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.HttpResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.response.mapper.DefaultResponseMapper;
import com.mulesoft.connectors.inference.internal.utils.ParseUtils;

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

  public Result<InputStream, LLMResponseAttributes> executeChatAnswerPrompt(TextGenerationConnection connection, String prompt,
                                                                            InputStream additionalRequestAttributes)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection,
                                               payloadHelper.buildChatAnswerPromptPayload(connection, prompt,
                                                                                          ParseUtils
                                                                                              .parseAdditionalRequestAttributes(additionalRequestAttributes,
                                                                                                                                objectMapper)));
  }

  public Result<InputStream, LLMResponseAttributes> executeChatCompletion(TextGenerationConnection connection,
                                                                          InputStream messages,
                                                                          InputStream additionalRequestAttributes)
      throws IOException, TimeoutException {

    TextGenerationRequestPayloadDTO requestPayloadDTO =
        payloadHelper.parseAndBuildChatCompletionPayload(connection, messages,
                                                         ParseUtils.parseAdditionalRequestAttributes(additionalRequestAttributes,
                                                                                                     objectMapper));

    return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
  }

  public Result<InputStream, LLMResponseAttributes> definePromptTemplate(TextGenerationConnection connection, String template,
                                                                         String instructions, String data,
                                                                         InputStream additionalRequestAttributes)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection,
                                               payloadHelper.buildPromptTemplatePayload(connection, template, instructions,
                                                                                        data,
                                                                                        ParseUtils
                                                                                            .parseAdditionalRequestAttributes(additionalRequestAttributes,
                                                                                                                              objectMapper)));
  }

  public Result<InputStream, LLMResponseAttributes> executeToolsNativeTemplate(TextGenerationConnection connection,
                                                                               String template, String instructions,
                                                                               String data, InputStream tools,
                                                                               InputStream additionalRequestAttributes)
      throws IOException, TimeoutException {

    return executeToolsRequestAndFormatResponse(connection, payloadHelper
        .buildToolsTemplatePayload(connection, template, instructions, data, tools,
                                   ParseUtils.parseAdditionalRequestAttributes(additionalRequestAttributes, objectMapper)));
  }


  public Result<InputStream, LLMResponseAttributes> executeMcpTools(TextGenerationConnection connection,
                                                                    Scheduler scheduler,
                                                                    ExtensionsClient extensionsClient,
                                                                    List<McpConfig> mcpConfigs, String template,
                                                                    String instructions, String data,
                                                                    InputStream additionalRequestAttributes) {

    return mcpHelper.getTools(mcpConfigs, scheduler, extensionsClient)
        .thenApply(collectedTools -> {
          try {
            var toolFunctions = collectedTools.values().stream()
                .map(mcpTool -> new FunctionDefinitionRecord("function", mcpTool.function()))
                .toList();

            // send tools list to mcp
            TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper
                .buildToolsTemplatePayload(connection, template, instructions, data, toolFunctions,
                                           ParseUtils.parseAdditionalRequestAttributes(additionalRequestAttributes,
                                                                                       objectMapper));

            logger.debug(PAYLOAD_LOGGER_MSG, requestPayloadDTO);

            TextResponseDTO chatResponse = executeChatRequest(connection, requestPayloadDTO);

            List<ToolResult> toolExecutionResult = mcpHelper.executeTools(collectedTools,
                                                                          responseParser.mapToolCalls(chatResponse,
                                                                                                      // don't pass collected
                                                                                                      // tools to keep prefixed
                                                                                                      // func name
                                                                                                      null),
                                                                          extensionsClient);
            logger.debug("Tool Execution result:{}", toolExecutionResult);
            return ResponseHelper.createLLMResponse(
                                                    objectMapper.writeValueAsString(responseParser
                                                        .mapMcpExecuteToolsResponse(chatResponse, toolExecutionResult,
                                                                                    collectedTools)),
                                                    responseParser.mapTokenUsageFromResponse(chatResponse),
                                                    responseParser.mapAdditionalAttributes(chatResponse,
                                                                                           connection.getModelName()));
          } catch (Exception e) {
            throw new ModuleException("Error processing MCP tools", InferenceErrorType.MCP_SERVER_ERROR, e);
          }
        })
        .exceptionally(throwable -> {
          // Handle exceptions from getTools() or any upstream failures
          Throwable cause = throwable.getCause() != null ? throwable.getCause() : throwable;
          throw new ModuleException("Error in getting MCP tools", InferenceErrorType.MCP_SERVER_ERROR, cause);
        })
        .join();
  }

  private Result<InputStream, LLMResponseAttributes> executeToolsRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                          TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    return executeChatRequestAndFormatResponse(connection, requestPayloadDTO);
  }

  private Result<InputStream, LLMResponseAttributes> executeChatRequestAndFormatResponse(TextGenerationConnection connection,
                                                                                         TextGenerationRequestPayloadDTO requestPayloadDTO)
      throws IOException, TimeoutException {

    TextResponseDTO chatResponse = executeChatRequest(connection, requestPayloadDTO);

    return ResponseHelper.createLLMResponse(
                                            objectMapper.writeValueAsString(responseParser.mapChatResponse(chatResponse)),
                                            responseParser.mapTokenUsageFromResponse(chatResponse),
                                            responseParser.mapAdditionalAttributes(chatResponse, connection.getModelName()));
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
