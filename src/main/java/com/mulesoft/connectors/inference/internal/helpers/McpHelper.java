package com.mulesoft.connectors.inference.internal.helpers;

import com.mulesoft.connectors.inference.api.request.Function;
import com.mulesoft.connectors.inference.api.request.FunctionDefinitionRecord;
import com.mulesoft.connectors.inference.api.request.Parameters;
import com.mulesoft.connectors.inference.api.request.Property;
import com.mulesoft.connectors.inference.api.response.ToolCall;
import com.mulesoft.connectors.inference.api.response.ToolResult;
import com.mulesoft.connectors.inference.internal.connection.types.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.mcp.ServerInfo;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McpHelper {

  private static final Logger logger = LoggerFactory.getLogger(McpHelper.class);

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
            List<FunctionDefinitionRecord> tools = getMcpTools(url, connection.getTimeout());
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

  public List<FunctionDefinitionRecord> getMcpTools(String mcpServerUrl, int timeout) {

    McpToolConverter converter = new McpToolConverter();

    try (McpSyncClient client = establishClientMCP(mcpServerUrl, timeout)) {

      return Optional.ofNullable(client.listTools())
          .map(McpSchema.ListToolsResult::tools)
          .map(tools -> tools.stream()
              .map(tool -> new FunctionDefinitionRecord(
                                                        "function",
                                                        getToolsFunction(tool, converter.convertToolToPropertyMap(tool))))
              .toList())
          .orElse(Collections.emptyList());
    }
  }

  public List<ToolResult> executeTools(List<ServerInfo> mcpToolsArrayByServer, List<ToolCall> toolCallList, int timeout) {

    List<ToolResult> toolResults = new ArrayList<>();

    if (toolCallList != null) {
      for (ToolCall toolCall : toolCallList) {
        String functionName = toolCall.function().name();
        Map<String, Object> arguments = getArgumentsAsMap(toolCall.function().arguments());

        ServerInfo serverInfo = findServerInfoForTool(mcpToolsArrayByServer, functionName);
        if (serverInfo != null) {
          String serverUrl = serverInfo.serverUrl();
          String serverName = serverInfo.serverName();

          try (McpSyncClient client = establishClientMCP(serverUrl, timeout)) {

            McpSchema.CallToolResult result = executeMcpCallToolRequest(client, functionName, arguments);

            Object contentObj = null;
            for (McpSchema.Content content : result.content()) {
              if (content instanceof McpSchema.TextContent textContent) {
                contentObj = textContent.text();
                logger.debug("TextContent is {} ", contentObj);
                break;
              }
            }
            ToolResult resultObject = new ToolResult(functionName, contentObj, serverUrl, serverName, Instant.now());
            toolResults.add(resultObject);
          }
        }
      }
    }
    return toolResults;
  }

  private static McpSchema.CallToolResult executeMcpCallToolRequest(McpSyncClient client, String functionName,
                                                                    Map<String, Object> arguments) {
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
      return objectMapper.readValue(jsonString, new TypeReference<>() {});
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
                                               tool.inputSchema().additionalProperties());

    Function fun = new Function(
                                tool.name(),
                                tool.description(),
                                parameters);
    logger.debug("20250616 function: {}", fun);
    return fun;
  }

  private McpSyncClient establishClientMCP(String mcpServerUrl, int requestTimeout) {

    HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(mcpServerUrl)
        .build();

    McpSyncClient client = McpClient.sync(transport)
        .requestTimeout(Duration.ofSeconds(requestTimeout))
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
            .filter(String.class::isInstance)
            .map(String.class::cast).toList())
        .filter(list -> !list.isEmpty())
        .orElse(null);
  }

  /**
   * Converter class for transforming MCP Tool schema to Map<String, Property> Handles nested objects, arrays, and complex JSON
   * Schema structures
   */
  static class McpToolConverter {

    private final ObjectMapper objectMapper;

    public McpToolConverter() {
      this.objectMapper = new ObjectMapper();
    }

    /**
     * Converts MCP Tool inputSchema to Map<String, Property>
     *
     * @param tool The MCP Tool containing the inputSchema
     * @return Map of property names to Property objects
     */
    public Map<String, Property> convertToolToPropertyMap(McpSchema.Tool tool) {
      Map<String, Property> propertyMap = new HashMap<>();

      try {
        // Get the inputSchema from the tool
        Object inputSchema = tool.inputSchema();

        if (inputSchema == null) {
          return propertyMap;
        }

        // Convert to JsonNode for easier manipulation
        JsonNode schemaNode = objectMapper.valueToTree(inputSchema);

        // Extract properties from the schema
        JsonNode propertiesNode = schemaNode.get("properties");
        if (propertiesNode != null && propertiesNode.isObject()) {
          Iterator<Map.Entry<String, JsonNode>> fields = propertiesNode.fields();
          while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String propertyName = field.getKey();
            JsonNode propertyNode = field.getValue();

            Property property = convertJsonNodeToProperty(propertyNode);
            propertyMap.put(propertyName, property);
          }
        }

      } catch (Exception e) {
        throw new RuntimeException("Failed to convert MCP Tool to Property Map", e);
      }

      return propertyMap;
    }

    /**
     * Converts a JsonNode representing a JSON Schema property to a Property record Handles nested objects, arrays, and all JSON
     * Schema types
     *
     * @param propertyNode The JsonNode containing property definition
     * @return Property record instance
     */
    private Property convertJsonNodeToProperty(JsonNode propertyNode) {
      // Extract type
      String type = extractStringValue(propertyNode, "type");

      // Extract description
      String description = extractStringValue(propertyNode, "description");

      // Extract enum values
      List<String> enumValues = extractEnumValues(propertyNode);

      // Extract nested properties (for object types only)
      Map<String, Property> nestedProperties = extractNestedProperties(propertyNode);

      // Extract items for array types separately
      Property itemsProperty = null;
      if ("array".equals(type)) {
        JsonNode itemsNode = propertyNode.get("items");
        if (itemsNode != null) {
          itemsProperty = convertJsonNodeToProperty(itemsNode);
        }
      }

      return new Property(type, description, enumValues, nestedProperties, itemsProperty);
    }

    /**
     * Extracts string value from JsonNode
     *
     * @param node The JsonNode to extract from
     * @param fieldName The field name to extract
     * @return String value or null if not present
     */
    private String extractStringValue(JsonNode node, String fieldName) {
      JsonNode fieldNode = node.get(fieldName);
      return (fieldNode != null && !fieldNode.isNull()) ? fieldNode.asText() : null;
    }

    /**
     * Extracts enum values from JsonNode
     *
     * @param propertyNode The property node that may contain enum values
     * @return List of enum values or null if not present
     */
    private List<String> extractEnumValues(JsonNode propertyNode) {
      List<String> enumValues = new ArrayList<>();
      JsonNode enumNode = propertyNode.get("enum");

      if (enumNode != null && enumNode.isArray()) {
        for (JsonNode enumValue : enumNode) {
          enumValues.add(enumValue.asText());
        }
      }

      return enumValues.isEmpty() ? null : enumValues;
    }

    /**
     * Extracts nested properties for object-type properties Excludes array items as they are handled separately
     *
     * @param propertyNode The property node to extract nested properties from
     * @return Map of nested properties or null if none exist
     */
    private Map<String, Property> extractNestedProperties(JsonNode propertyNode) {
      Map<String, Property> nestedProperties = new HashMap<>();

      // Handle object properties
      JsonNode propertiesNode = propertyNode.get("properties");
      if (propertiesNode != null && propertiesNode.isObject()) {
        Iterator<Map.Entry<String, JsonNode>> fields = propertiesNode.fields();
        while (fields.hasNext()) {
          Map.Entry<String, JsonNode> field = fields.next();
          String nestedPropertyName = field.getKey();
          JsonNode nestedPropertyNode = field.getValue();

          Property nestedProperty = convertJsonNodeToProperty(nestedPropertyNode);
          nestedProperties.put(nestedPropertyName, nestedProperty);
        }
      }

      // Handle anyOf, oneOf, and allOf schemas
      handleSchemaComposition(propertyNode, nestedProperties);

      return nestedProperties.isEmpty() ? null : nestedProperties;
    }

    /**
     * Handles JSON Schema composition keywords (anyOf, oneOf, allOf)
     *
     * @param propertyNode The property node that may contain composition schemas
     * @param nestedProperties The map to add composition schemas to
     */
    private void handleSchemaComposition(JsonNode propertyNode, Map<String, Property> nestedProperties) {
      String[] compositionKeywords = {"anyOf", "oneOf", "allOf"};

      for (String keyword : compositionKeywords) {
        JsonNode compositionNode = propertyNode.get(keyword);
        if (compositionNode != null && compositionNode.isArray()) {
          List<Property> compositionSchemas = new ArrayList<>();

          for (JsonNode schemaNode : compositionNode) {
            Property compositionProperty = convertJsonNodeToProperty(schemaNode);
            compositionSchemas.add(compositionProperty);
          }

          // Store composition schemas as a special nested property
          if (!compositionSchemas.isEmpty()) {
            // For simplicity, we'll store the first schema in the composition
            nestedProperties.put(keyword, compositionSchemas.get(0));
          }
        }
      }
    }

    /**
     * Utility method to check if a property has nested structure
     *
     * @param property The property to check
     * @return true if the property has nested properties or is an array
     */
    public boolean hasNestedStructure(Property property) {
      return (property.properties() != null && !property.properties().isEmpty()) || property.items() != null;
    }

    /**
     * Utility method to extract all property names recursively Useful for flattening nested property structures
     *
     * @param propertyMap The property map to extract names from
     * @param prefix Optional prefix for nested property names
     * @return Set of all property names including nested ones
     */
    public java.util.Set<String> extractAllPropertyNames(Map<String, Property> propertyMap, String prefix) {
      java.util.Set<String> propertyNames = new java.util.HashSet<>();

      for (Map.Entry<String, Property> entry : propertyMap.entrySet()) {
        String propertyName = prefix != null ? prefix + "." + entry.getKey() : entry.getKey();
        propertyNames.add(propertyName);

        // Recursively extract nested property names
        Property property = entry.getValue();
        if (property.properties() != null && !property.properties().isEmpty()) {
          propertyNames.addAll(extractAllPropertyNames(property.properties(), propertyName));
        }
        // Handle items for arrays
        if (property.items() != null) {
          propertyNames.add(propertyName + ".items");
          if (property.items().properties() != null && !property.items().properties().isEmpty()) {
            propertyNames.addAll(extractAllPropertyNames(property.items().properties(), propertyName + ".items"));
          }
        }
      }

      return propertyNames;
    }
  }

}
