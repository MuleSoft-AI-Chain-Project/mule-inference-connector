package com.mulesoft.connectors.inference.internal.helpers.mcp;

import static com.mulesoft.connectors.inference.internal.error.InferenceErrorType.MCP_SERVER_ERROR;

import static java.util.concurrent.CompletableFuture.completedFuture;

import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.runtime.extension.api.client.ExtensionsClient;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;

import com.mulesoft.connectors.inference.api.mcp.McpConfig;
import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.FunctionSchema;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.dto.mcp.McpServerToolDTO;
import com.mulesoft.connectors.inference.internal.dto.mcp.McpToolRecord;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McpHelper {

  private static final Logger logger = LoggerFactory.getLogger(McpHelper.class);

  private static final String MCP = "MCP";

  private final ObjectMapper objectMapper;

  public McpHelper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  private final Map<String, List<McpToolRecord>> toolsByServer = new ConcurrentHashMap<>();

  public CompletableFuture<Map<String, McpToolRecord>> getTools(List<McpConfig> mcpConfigs, Scheduler scheduler,
                                                                ExtensionsClient extensionsClient) {
    if (null == mcpConfigs || mcpConfigs.isEmpty()) {
      return completedFuture(Collections.emptyMap());
    }
    return new McpDiscovery(mcpConfigs, scheduler, extensionsClient).getDiscoveredTools();
  }

  public List<ToolResult> executeTools(Map<String, McpToolRecord> collectedTools, List<ToolCall> toolCalls,
                                       ExtensionsClient extensionsClient) {
    if (toolCalls == null || toolCalls.isEmpty()) {
      return new ArrayList<>();
    }

    return toolCalls.stream()
        .map(toolCall -> executeToolCall(toolCall, collectedTools, extensionsClient))
        .toList();
  }

  private ToolResult executeToolCall(ToolCall toolCall, Map<String, McpToolRecord> collectedTools,
                                     ExtensionsClient extensionsClient) {
    String toolName = toolCall.function().name();

    return Optional.ofNullable(collectedTools.get(toolName))
        .map(tool -> parseArgumentsAndExecute(tool, toolCall, extensionsClient))
        .orElseThrow(() -> {
          logger.error("Tool '{}' not found in collected tools", toolName);
          return new ModuleException("Tool '" + toolName + "' not found in collected tools", MCP_SERVER_ERROR);
        });
  }


  private ToolResult parseArgumentsAndExecute(McpToolRecord tool, ToolCall toolCall, ExtensionsClient extensionsClient) {

    logger.debug("Executing tool call:{}", tool);
    try {
      Map<String, Object> args = objectMapper.readValue(toolCall.function().arguments(),
                                                        objectMapper.getTypeFactory().constructMapType(Map.class, String.class,
                                                                                                       Object.class));
      return executeToolWithErrorHandling(tool, args, extensionsClient);
    } catch (JsonProcessingException e) {
      throw new ModuleException("Failed to execute tool '" + tool.getName() + "': " + e.getMessage(), MCP_SERVER_ERROR, e);
    }
  }

  /**
   * Executes tool with comprehensive error handling.
   */
  private ToolResult executeToolWithErrorHandling(McpToolRecord tool, Map<String, Object> args,
                                                  ExtensionsClient extensionsClient) {
    return invokeMcpCallTool(tool, args, extensionsClient)
        .exceptionally(toolException -> {
          throw new ModuleException("Error executing tool '" + tool.getName() + "': " + toolException.getMessage(),
                                    MCP_SERVER_ERROR, toolException);
        })
        .join();
  }

  private class McpDiscovery {

    private final List<McpConfig> mcpConfigs;
    private final Map<String, McpToolRecord> discoveredTools = new ConcurrentHashMap<>();
    private final AtomicInteger countDown;
    private final CompletableFuture<Map<String, McpToolRecord>> future = new CompletableFuture<>();
    private final Scheduler scheduler;
    private final ExtensionsClient extensionsClient;

    private McpDiscovery(List<McpConfig> mcpConfigs, Scheduler scheduler, ExtensionsClient extensionsClient) {
      this.mcpConfigs = mcpConfigs;
      countDown = new AtomicInteger(mcpConfigs.size());
      this.scheduler = scheduler;
      this.extensionsClient = extensionsClient;
    }

    public CompletableFuture<Map<String, McpToolRecord>> getDiscoveredTools() {
      try {
        for (McpConfig mcpConfig : mcpConfigs) {
          final String mcpConfigRef = mcpConfig.getMcpClientConfigRef();
          List<McpToolRecord> mcpMcpToolRecords = toolsByServer.get(mcpConfigRef);
          if (mcpMcpToolRecords != null) {
            collect(mcpMcpToolRecords);
          } else {
            scheduler.submit(() -> invokeMcpListTools(mcpConfig));
          }
        }
      } catch (Exception ex) {
        future.completeExceptionally(ex);
      }
      return future;
    }

    /**
     * Asynchronously fetches available tools from the specified MCP server using MCP Connector.
     *
     * <p>
     * This method executes the "listTools" operation on the MCP connector using server configuration details and processes the
     * response asynchronously. The fetched tools are converted to {@link McpToolRecord} instances and added to the discovered
     * tools collection.
     * </p>
     *
     * @param mcpConfig the MCP server configuration from which to fetch tools. Must contain a valid MCP client configuration
     *        reference.
     */
    private void invokeMcpListTools(McpConfig mcpConfig) {
      final String mcpConfigRef = mcpConfig.getMcpClientConfigRef();

      extensionsClient.execute(MCP, "listTools", params -> params.withConfigRef(mcpConfigRef))
          .whenComplete((result, t) -> Optional.ofNullable(t)
              .ifPresentOrElse(
                               throwable -> handleToolParsingException(countDown, throwable, mcpConfigRef),
                               () -> processToolsResult(result, mcpConfigRef)));
    }

    private void processToolsResult(Result<Object, Object> result, String mcpConfigRef) {
      try {
        List<McpToolRecord> mcpTools = createTools(result, mcpConfigRef);
        Optional.of(mcpTools)
            .filter(tools -> !tools.isEmpty())
            .ifPresent(tools -> {
              collect(tools);
              toolsByServer.put(mcpConfigRef, tools);
            });
      } catch (Exception e) {
        handleToolParsingException(countDown, e, mcpConfigRef);
      }
    }

    private List<McpToolRecord> createTools(Result<Object, Object> result, String mcpConfigRef) {
      Object resultOutput = result.getOutput();
      if (!(resultOutput instanceof Iterator<?> iterator)) {
        logger.error("Expected Iterator but got: {}", resultOutput.getClass().getName());
        return new ArrayList<>();
      }

      return StreamSupport.stream(
                                  Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
          .map(obj -> {
            try {
              McpServerToolDTO serverToolDTO = objectMapper.convertValue(obj, McpServerToolDTO.class);

              String originalToolName = serverToolDTO.name();
              String description = serverToolDTO.description();

              logger.debug("Server:{}, Tool details from server: {}", mcpConfigRef, serverToolDTO);

              FunctionSchema toolSchema =
                  serverToolDTO.inputSchema() != null ? objectMapper.readValue(serverToolDTO.inputSchema(), FunctionSchema.class)
                      : null;

              return new McpToolRecord(
                                       originalToolName,
                                       description,
                                       mcpConfigRef,
                                       new Function(mcpConfigRef + "__" + originalToolName, description, toolSchema));
            } catch (JacksonException e) {
              logger.error("Failed to convert object to McpToolRecord: {}", obj, e);
              return null; // Return null for failed conversions
            }
          })
          .filter(Objects::nonNull) // Filter out null values from failed conversions
          .toList();
    }

    private void collect(Collection<McpToolRecord> mcpTools) {
      mcpTools.forEach(mcpTool -> discoveredTools.put(mcpTool.getName(), mcpTool));

      if (countDown.decrementAndGet() <= 0) {
        future.complete(discoveredTools);
      }
    }

    private void handleToolParsingException(AtomicInteger countDown, Throwable t, String mcpConfigRef) {
      future.completeExceptionally(
                                   new ModuleException(
                                                       "Exception obtaining toolList from MCP client config %s: %s"
                                                           .formatted(mcpConfigRef, t.getMessage()),
                                                       MCP_SERVER_ERROR, t));
      countDown.set(-1);
    }
  }

  /**
   * Executes a tool by calling the MCP connector with the specified arguments.
   *
   * <p>
   * This method executes the "callTool" operation on the MCP connector using server configuration details and the parameters:
   * toolName, arguments. The fetched tools are converted to {@link McpToolRecord} instances and added to the discovered tools
   * collection.
   * </p>
   *
   * @param tool the McpToolRecord containing tool configuration
   * @param args the arguments to pass to the tool
   * @param extensionsClient the extensions client for executing the MCP operation
   * @return CompletableFuture containing the tool execution result
   */
  private CompletableFuture<ToolResult> invokeMcpCallTool(McpToolRecord tool, Map<String, Object> args,
                                                          ExtensionsClient extensionsClient) {
    return extensionsClient.execute(MCP, "callTool",
                                    params -> params.withConfigRef(tool.configRef())
                                        .withParameter("toolName", tool.originalName())
                                        .withParameter("arguments", args))
        .thenApply(result -> new ToolResult(tool.originalName(), result.getOutput(),
                                            tool.configRef(), Instant.now()));
  }
}
