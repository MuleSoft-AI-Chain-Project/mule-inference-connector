package com.mulesoft.connectors.inference.internal.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.api.request.Parameters;
import com.mulesoft.connectors.inference.api.request.Property;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.mcp.ServerInfo;
import com.mulesoft.connectors.inference.internal.utils.ProviderUtils;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class McpHelper {

    private static final Logger logger = LoggerFactory.getLogger(ProviderUtils.class);

    public static final int MCP_CLIENT_REQUEST_TIMEOUT = 60;
    private final ObjectMapper objectMapper;
    private List<FunctionDefinitionRecord> mcpTools = null;
    private List<ServerInfo> mcpToolsArrayByServer = null;
    private boolean mcpToolsLoaded = false;

    public McpHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<FunctionDefinitionRecord> getMcpToolsFromMultiple(TextGenerationConnection connection) {
        if (!mcpToolsLoaded) {
            mcpToolsArrayByServer = new ArrayList<>();
            mcpTools = new ArrayList<>();

            Map<String, String> mcpServers = connection.getMcpSseServers();
            String httpPattern = "^https?://.*";

            mcpServers.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && entry.getValue().matches(httpPattern))
                    .forEach(entry -> {
                        String url = entry.getValue();
                        String key = entry.getKey();
                        List<FunctionDefinitionRecord> tools = getMcpTools(url);
                        mcpTools.addAll(tools);

                        boolean serverExists = mcpToolsArrayByServer.stream()
                                .anyMatch(server -> server.serverUrl().equals(url));

                        if (!serverExists) {
                            mcpToolsArrayByServer.add(new ServerInfo(url, key, tools));
                        }
                    });
            mcpToolsLoaded = true;
        }
        return mcpTools;
    }

    public List<ServerInfo> getMcpToolsArrayByServer() {
        return mcpToolsArrayByServer;
    }

    public List<FunctionDefinitionRecord> getMcpTools(String mcpServerUrl) {

        try(McpSyncClient client = establishClientMCP(mcpServerUrl)) {

            return Optional.ofNullable(client.listTools())
                    .map(McpSchema.ListToolsResult::tools)
                    .map(tools -> tools.stream()
                            .map(tool -> new FunctionDefinitionRecord(
                                    "function",
                                    getToolsFunction(tool, getPropertiesMap(tool))
                            )).toList())
                    .orElse(Collections.emptyList());
        }
    }

    public List<ToolResult> executeTools(List<ServerInfo> mcpToolsArrayByServer, List<ToolCall> toolCallList) {

        logger.debug("ExecuteTools - Response from the tools server: {}", toolCallList);

        List<ToolResult> toolResults = new ArrayList<>();

        for (ToolCall toolCall : toolCallList) {
            String functionName = toolCall.function().name();
            Map<String, Object> arguments = getArgumentsAsMap(toolCall.function().arguments());

            ServerInfo serverInfo = findServerInfoForTool(mcpToolsArrayByServer, functionName);
            String serverUrl = serverInfo.serverUrl();
            String serverName = serverInfo.serverName();

            try(McpSyncClient client = establishClientMCP(serverUrl)) {

                McpSchema.CallToolResult result = executeMcpCallToolRequest(client, functionName, arguments);

                Object contentObj = null;
                for (McpSchema.Content content : result.content()) {
                    if (content instanceof McpSchema.TextContent textContent) {
                        logger.debug("TextContent is {} ", textContent.text());
                            contentObj = textContent.text();
                            break;
                    }
                }
                ToolResult resultObject = new ToolResult(functionName,contentObj,serverUrl,serverName, Instant.now());
                toolResults.add(resultObject);
            }
        }
        return toolResults;
    }

    private static McpSchema.CallToolResult executeMcpCallToolRequest(McpSyncClient client, String functionName, Map<String, Object> arguments) {
        McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(functionName, arguments);
        return client.callTool(request);
    }

    private static ServerInfo findServerInfoForTool(List<ServerInfo> servers, String toolName) {
        return servers.stream()
                .filter(server -> server.serverTools().stream()
                        .anyMatch(tool -> tool.function().name().equals(toolName)))
                .findFirst()
                .orElse(null);
    }

    private Map<String, Object> getArgumentsAsMap(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON: {}. Returning empty map for arguments", e.getMessage());
            return Collections.emptyMap();
        }
    }

    private Function getToolsFunction(McpSchema.Tool tool, Map<String, Property> properties) {
        Parameters parameters = new Parameters(
                "object",
                properties,
                new ArrayList<>(tool.inputSchema().properties().keySet()),
                tool.inputSchema().additionalProperties() != null &&
                        tool.inputSchema().additionalProperties()
        );
        return new Function(
                tool.name(),
                tool.description(),
                parameters
        );
    }

    private Map<String, Property> getPropertiesMap(McpSchema.Tool tool) {

        return Optional.ofNullable(tool.inputSchema().properties())
                .map(props -> props.entrySet().stream()
                        .filter(entry -> entry.getValue() instanceof Map<?, ?>)
                        .map(this::getMapEntry)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (v1, v2) -> v1, HashMap::new)))
                .orElse(new HashMap<>());
    }

    private Map.@Nullable Entry<String, Property> getMapEntry(Map.Entry<String, Object> entry) {
        Map<String, Object> propMap = convertToTypedMap((Map<?, ?>) entry.getValue());
        if (propMap == null) {
            return null;
        }
        String type = getStringValue(propMap, "type");
        if (type == null) {
            return null;
        }
        return Map.entry(
                entry.getKey(),
                new Property(
                        type,
                        getStringValue(propMap, "description"),
                        getEnumValues(propMap)));
    }

    private McpSyncClient establishClientMCP(String mcpServerUrl){

        HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(mcpServerUrl)
                .build();

        McpSyncClient client = McpClient.sync(transport)
                .requestTimeout(Duration.ofSeconds(MCP_CLIENT_REQUEST_TIMEOUT))
                .capabilities(getClientCapabilities())
                .build();

        client.initialize();
        return client;
    }

    private McpSchema.ClientCapabilities getClientCapabilities() {
        return McpSchema.ClientCapabilities.builder()
                .roots(true)
                .build();
    }

    private Map<String, Object> convertToTypedMap(Map<?, ?> rawMap) {
        Map<String, Object> typedMap = new HashMap<>();
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (entry.getKey() instanceof String key) {
                typedMap.put(key, entry.getValue());
            } else {
                return null; // Invalid key type
            }
        }
        return typedMap;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        return Optional.ofNullable(map.get(key))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(null);
    }

    private List<String> getEnumValues(Map<String, Object> map) {
        return Optional.ofNullable(map.get("enum"))
                .filter(List.class::isInstance)
                .<List<?>>map(list -> (List<?>) list)
                .map(list -> list.stream()
                        .<String>filter(String.class::isInstance)
                        .map(String.class::cast).toList())
                .filter(list -> !list.isEmpty())
                .orElse(null);
    }
}
