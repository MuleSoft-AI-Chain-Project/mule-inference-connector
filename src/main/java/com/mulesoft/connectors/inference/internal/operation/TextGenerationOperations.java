package com.mulesoft.connectors.inference.internal.operation;

import static org.mule.runtime.extension.api.annotation.param.MediaType.APPLICATION_JSON;

import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.dsl.xml.ParameterDsl;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.metadata.fixed.InputJsonType;
import org.mule.runtime.extension.api.annotation.metadata.fixed.OutputJsonType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.client.ExtensionsClient;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.mcp.McpConfig;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.config.TextGenerationConfig;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.error.provider.TextGenerationErrorTypeProvider;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletionException;

/**
 * This class contains operations for the inference connector. Each public method represents an extension operation.
 */
@Throws(TextGenerationErrorTypeProvider.class)
public class TextGenerationOperations {

  /**
   * Chat completions by messages array including system, users messages i.e. conversation history
   *
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
                                                                   @Connection TextGenerationConnection connection,
                                                                   @InputJsonType(
                                                                       schema = "api/request/ChatCompletionMessagesSchema.json") @Content(
                                                                           primary = true) InputStream messages,
                                                                   @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream additionalRequestAttributes)
      throws ModuleException {
    try {
      return connection.getService().getTextGenerationServiceInstance().executeChatCompletion(connection, messages,
                                                                                              additionalRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing chat completion",
                                InferenceErrorType.CHAT_OPERATION_FAILURE, e);
    }
  }

  /**
   * Simple chat answer for a single prompt
   *
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
                                                                     @Connection TextGenerationConnection connection,
                                                                     @Content(primary = true) String prompt,
                                                                     @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream chatRequestAttributes)
      throws ModuleException {
    try {
      return connection.getService().getTextGenerationServiceInstance().executeChatAnswerPrompt(connection, prompt,
                                                                                                chatRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing chat answer prompt",
                                InferenceErrorType.CHAT_OPERATION_FAILURE, e);
    }
  }

  /**
   * Define a prompt template with instructions and data
   *
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
                                                                   @Content(primary = true) String data,
                                                                   @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream promptRequestAttributes)

      throws ModuleException {
    try {
      return connection.getService().getTextGenerationServiceInstance().definePromptTemplate(connection, template, instructions,
                                                                                             data, promptRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing define prompt template",
                                InferenceErrorType.CHAT_OPERATION_FAILURE, e);
    }
  }

  /**
   * Define a tools template with instructions, data and tools
   *
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
  @Summary("[Tools] Native Template (Reasoning only)")
  public Result<InputStream, LLMResponseAttributes> toolsTemplate(
                                                                  @Connection TextGenerationConnection connection,
                                                                  @Content String template,
                                                                  @Content String instructions,
                                                                  @Content(primary = true) String data,
                                                                  @Content @InputJsonType(
                                                                      schema = "api/request/ToolsDefinition.json") @Summary("JSON Array defining the tools set to be used in the template so that the LLM can use them if required") InputStream tools,
                                                                  @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream toolsRequestAttributes)
      throws ModuleException {
    try {
      return connection.getService().getTextGenerationServiceInstance().executeToolsNativeTemplate(connection, template,
                                                                                                   instructions, data, tools,
                                                                                                   toolsRequestAttributes);
    } catch (ModuleException e) {
      throw e;
    } catch (Exception e) {
      throw new ModuleException("Error in executing operation Tools native template",
                                InferenceErrorType.TOOLS_OPERATION_FAILURE, e);
    }
  }

  /**
   * Execute MCP tools using a defined template with instructions, data and tools (fetched directly from MCP servers)
   *
   * @param connection the connector connection
   * @param template the template string for tool execution
   * @param instructions instructions for the LLM
   * @param data the primary data content
   * @return result containing the LLM response
   * @throws ModuleException if an error occurs during the operation
   */
  @MediaType(value = APPLICATION_JSON, strict = false)
  @Alias("Mcp-tools-native-template")
  @DisplayName("[MCP] Tooling")
  @OutputJsonType(schema = "api/response/McpToolingResponse.json")
  @Summary("Run tools using your defined prompt template")
  public Result<InputStream, LLMResponseAttributes> mcpToolsTemplate(@Config TextGenerationConfig config,
                                                                     @Connection TextGenerationConnection connection,
                                                                     @ParameterDsl(
                                                                         allowReferences = false) List<McpConfig> mcpConfigReferences,
                                                                     @Content String template,
                                                                     @Content String instructions,
                                                                     @Content(primary = true) String data,
                                                                     @Content @Optional @DisplayName("Additional Request Attributes") @Summary("JSON object with additional request attributes that will be flattened into the root level of the request payload") InputStream mcpRequestAttributes,
                                                                     ExtensionsClient extensionsClient)
      throws ModuleException {
    Scheduler scheduler = null;
    try {
      scheduler = config.getSchedulerService().ioScheduler(config.getSchedulerConfig().withName("mcp-discovery-scheduler"));
      return connection.getService().getTextGenerationServiceInstance().executeMcpTools(connection, scheduler,
                                                                                        extensionsClient,
                                                                                        mcpConfigReferences,
                                                                                        template, instructions, data,
                                                                                        mcpRequestAttributes);
    } catch (CompletionException e) {
      // Unwrap CompletionException to get the original ModuleException
      Throwable cause = e.getCause();
      if (cause instanceof ModuleException moduleException) {
        throw moduleException;
      } else {
        throw new ModuleException("Error in executing operation MCP tooling",
                                  InferenceErrorType.MCP_TOOLS_OPERATION_FAILURE, cause != null ? cause : e);
      }
    } catch (Exception e) {
      throw new ModuleException("Error in executing operation MCP tooling", InferenceErrorType.MCP_TOOLS_OPERATION_FAILURE, e);
    } finally {
      if (scheduler != null)
        scheduler.stop();
    }
  }

}
