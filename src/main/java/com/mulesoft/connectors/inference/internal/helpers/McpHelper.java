package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.api.request.Parameters;
import com.mulesoft.connectors.inference.api.request.Property;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.mcp.ServerInfo;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class McpHelper {

    public static final int MCP_CLIENT_REQUEST_TIMEOUT = 60;
    private List<FunctionDefinitionRecord> mcpTools = null;
    private List<ServerInfo> mcpToolsArrayByServer = null;
    private boolean mcpToolsLoaded = false;

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

        var initializeResult = client.initialize();
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
