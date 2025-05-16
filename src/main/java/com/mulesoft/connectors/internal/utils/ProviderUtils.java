package com.mulesoft.connectors.internal.utils;

import com.mulesoft.connectors.internal.connection.BaseConnection;
import com.mulesoft.connectors.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.internal.connection.ModerationImageGenerationBase;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import org.jetbrains.annotations.NotNull;
import org.mule.runtime.http.api.client.HttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONArray;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;

/**
 * Utility class for provider-specific operations.
 */
public class ProviderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderUtils.class);
    public static final String GOOGLE_PROVIDER_TYPE = "Google";
    public static final String ANTHROPIC_PROVIDER_TYPE = "Anthropic";
    public static final String META_PROVIDER_TYPE = "Meta";

    static JSONArray mcpToolsArray = null;
    static JSONArray mcpToolsArrayByServer = null;
    static JSONArray mcpTools= null;
    static Boolean mcpToolsLoaded = false;

    /**
     * Check if the inference type is LLM_API
     * @param configuration the connector configuration
     * @return true if the inference type is LLM_API, false otherwise
     */
    public static boolean isllamaAPI(ChatCompletionBase configuration) {
        return "LLM_API".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is OLLAMA
     * @param configuration the connector configuration
     * @return true if the inference type is OLLAMA, false otherwise
     */
    public static boolean isOllama(ChatCompletionBase configuration) {
        return "OLLAMA".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is OLLAMA
     * @param configuration the connector configuration
     * @return true if the inference type is OLLAMA, false otherwise
     */
    public static boolean isHuggingFace(ChatCompletionBase configuration) {
        return "HUGGING_FACE".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is Anthropic
     * @param configuration the connector configuration
     * @return true if the inference type is Anthropic, false otherwise
     */
    public static boolean isAnthropic(ChatCompletionBase configuration) {
        return "ANTHROPIC".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is NVIDIA
     * @param configuration the connector configuration
     * @return true if the inference type is NVIDIA, false otherwise
     */
    public static boolean isNvidia(ChatCompletionBase configuration) {
        return "NVIDIA".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is Cohere
     * @param configuration the connector configuration
     * @return true if the inference type is Cohere, false otherwise
     */
    public static boolean isCohere(ChatCompletionBase configuration) {
        return "COHERE".equals(configuration.getInferenceType());
    }

    /**
     * Check if the inference type is VERTEX_AI_EXPRESS
     * @param configuration the connector configuration
     * @return true if the inference type is Cohere, false otherwise
     */
    public static boolean isVertexAIExpress(ChatCompletionBase configuration) {
        return "VERTEX_AI_EXPRESS".equals(configuration.getInferenceType());
    }

    public static boolean isVertexAI(ChatCompletionBase configuration) {
        return "VERTEX_AI".equals(configuration.getInferenceType());
    }
    
    
    //get the providers based on the models
    public static String getProviderByModel(String modelName) {
        LOGGER.debug("model name {}", modelName);

        if (modelName == null || modelName.isEmpty()) {
            return "Unknown";
        }

        String upperName = modelName.toUpperCase();
        
        if (upperName.startsWith("GEMINI")) {
            return GOOGLE_PROVIDER_TYPE;
        } else if (upperName.startsWith("CLAUDE")) {
            return ANTHROPIC_PROVIDER_TYPE;
        } else if (upperName.startsWith("META")) {
            return META_PROVIDER_TYPE;
        } else {
            return "Unknown";
        }
    }


    /**
     * Check if the inference type is OPENAI
     * @param configuration the connector configuration
     * @return true if the inference type is Cohere, false otherwise
     */
    public static boolean isOpenAI(ChatCompletionBase configuration) {
        return "OPENAI".equals(configuration.getInferenceType());

    }


    public static boolean isStabilityAI(ChatCompletionBase configuration) {
        return "STABILITY_AI".equals(configuration.getInferenceType());
    }

    public static boolean isXAI(ChatCompletionBase configuration) {
        return "XAI".equals(configuration.getInferenceType());
    }

    @Deprecated
    public static @NotNull ChatCompletionBase convertToBaseConnection(ModerationImageGenerationBase imageGenerationBase) {
        BaseConnectionImpl baseConnection = new BaseConnectionImpl();

        baseConnection.setHttpClient(imageGenerationBase.getHttpClient());
        baseConnection.setInferenceType(imageGenerationBase.getInferenceType());
        baseConnection.setApiKey(imageGenerationBase.getApiKey());
        baseConnection.setModelName(imageGenerationBase.getModelName());
        baseConnection.setTimeout(imageGenerationBase.getTimeout());

        return baseConnection;
    }

    public static @NotNull ChatCompletionBase convertToBaseConnection(BaseConnection connection) {
        BaseConnectionImpl baseConnectionImpl = new BaseConnectionImpl();

        baseConnectionImpl.setHttpClient(connection.getHttpClient());
        baseConnectionImpl.setInferenceType(connection.getInferenceType());
        baseConnectionImpl.setApiKey(connection.getApiKey());
        baseConnectionImpl.setModelName(connection.getModelName());
        baseConnectionImpl.setTimeout(connection.getTimeout());

        return baseConnectionImpl;
    }

    private static class BaseConnectionImpl implements ChatCompletionBase {
        private HttpClient httpClient;
        private String inferenceType;
        private String apiKey;
        private String modelName;
        private int timeout;
        private Number maxTokens;
        private Number temperature;
        private Number topP;
        private String azureAIFoundryApiVersion;
        private String azureAIFoundryResourceName;
        private String azureOpenaiDeploymentId;
        private String azureOpenaiResourceName;
        private String dataBricksModelUrl;
        private String dockerModelUrl;
        private String gpt4All;
        private String ibmWatsonApiVersion;
        private String ibmWatsonProjectID;
        private String lmStudio;
        private String ollamaUrl;
        private String openCompatibleURL;
        private String virtualKey;
        private String xnferenceUrl;
        private String vertexAIProjectId;
        private String vertexAILocationId;
        private String vertexAIServiceAccountKey;
        private Map<String, String> mcpSseServers;

        @Override
        public HttpClient getHttpClient() { return httpClient; }
        public void setHttpClient(HttpClient httpClient) { this.httpClient = httpClient; }

        @Override
        public String getInferenceType() { return inferenceType; }
        public void setInferenceType(String inferenceType) { this.inferenceType = inferenceType; }

        @Override
        public String getApiKey() { return apiKey; }
        public void setApiKey(String apiKey) { this.apiKey = apiKey; }

        @Override
        public String getModelName() { return modelName; }
        public void setModelName(String modelName) { this.modelName = modelName; }

        @Override
        public int getTimeout() { return timeout; }
        public void setTimeout(int timeout) { this.timeout = timeout; }

        @Override
        public Number getMaxTokens() { return maxTokens; }
        public void setMaxTokens(Number maxTokens) { this.maxTokens = maxTokens; }

        @Override
        public Number getTemperature() { return temperature; }
        public void setTemperature(Number temperature) { this.temperature = temperature; }

        @Override
        public Number getTopP() { return topP; }
        public void setTopP(Number topP) { this.topP = topP; }

        @Override
        public String getAzureAIFoundryApiVersion() {
            return azureAIFoundryApiVersion;
        }
        public void setAzureAIFoundryApiVersion(String azureAIFoundryApiVersion) { this.azureAIFoundryApiVersion = azureAIFoundryApiVersion; }

        @Override
        public String getAzureAIFoundryResourceName() {
            return azureAIFoundryResourceName;
        }
        public void setAzureAIFoundryResourceName(String azureAIFoundryResourceName) { this.azureAIFoundryResourceName = azureAIFoundryResourceName; }

        @Override
        public String getAzureOpenaiDeploymentId() {
            return azureOpenaiDeploymentId;
        }
        public void setAzureOpenaiDeploymentId(String azureOpenaiDeploymentId) { this.azureOpenaiDeploymentId = azureOpenaiDeploymentId; }

        @Override
        public String getAzureOpenaiResourceName() {
            return azureOpenaiResourceName;
        }
        public void setAzureOpenaiResourceName(String azureOpenaiResourceName) { this.azureOpenaiResourceName = azureOpenaiResourceName; }

        @Override
        public String getDataBricksModelUrl() { return dataBricksModelUrl; }

        public void dataBricksModelUrl(String dataBricksModelUrl) { this.dataBricksModelUrl = dataBricksModelUrl; }

        @Override
        public String getDockerModelUrl() {
            return dockerModelUrl;
        }
        public void setDockerModelUrl(String dockerModelUrl) { this.dockerModelUrl = dockerModelUrl; }

        @Override
        public String getGpt4All() {
            return gpt4All;
        }
        public void setGpt4All(String gpt4All) { this.gpt4All = gpt4All; }

        @Override
        public String getIBMWatsonApiVersion() {
            return ibmWatsonApiVersion;
        }
        public void setIBMWatsonApiVersion(String ibmWatsonApiVersion) { this.ibmWatsonApiVersion = ibmWatsonApiVersion; }

        @Override
        public String getibmWatsonProjectID() {
            return ibmWatsonProjectID;
        }
        public void setibmWatsonProjectID(String ibmWatsonProjectID) { this.ibmWatsonProjectID = ibmWatsonProjectID; }

        @Override
        public String getLmStudio() {
            return lmStudio;
        }
        public void setLmStudio(String lmStudio) { this.lmStudio = lmStudio; }

        @Override
        public String getOllamaUrl() {
            return ollamaUrl;
        }
        public void setOllamaUrl(String ollamaUrl) { this.ollamaUrl = ollamaUrl; }

        @Override
        public String getOpenAICompatibleURL() {
            return openCompatibleURL;
        }
        public void setOpenAICompatibleURL(String openCompatibleURL) { this.openCompatibleURL = openCompatibleURL; }

        @Override
        public String getVirtualKey() {
            return virtualKey;
        }
        public void setVirtualKey(String virtualKey) { this.virtualKey = virtualKey; }

        @Override
        public String getxinferenceUrl() {
            return xnferenceUrl;
        }
        public void setXinferenceUrl(String xnferenceUrl) { this.xnferenceUrl = xnferenceUrl; }
        
        @Override
        public String getVertexAIProjectId() { return vertexAIProjectId; }
        public void setVertexAIProjectId(String vertexAIProjectId) { this.vertexAIProjectId = vertexAIProjectId; }

        @Override
        public String getVertexAILocationId() { return vertexAILocationId; }        
        public void setVertexAILocationId(String vertexAILocationId) { this.vertexAILocationId = vertexAILocationId; }
        
        @Override
        public String getVertexAIServiceAccountKey() { return vertexAIServiceAccountKey; }
        public void setVertexAIServiceAccountKey(String vertexAIServiceAccountKey) { this.vertexAIServiceAccountKey = vertexAIServiceAccountKey; }

        @Override
        public Map<String, String> getMcpSseServers() { return mcpSseServers; }
        public void setMcpSseServers(Map<String, String> mcpSseServerUrl_5) { this.mcpSseServers = mcpSseServers; }

    }

    private static McpSyncClient establishClientMCP(String mcpServerUrl){

        HttpClientSseClientTransport transport = HttpClientSseClientTransport.builder(mcpServerUrl)
                .build();

        McpSyncClient client = McpClient.sync(transport)
                .requestTimeout(Duration.ofSeconds(60))
                .capabilities(McpSchema.ClientCapabilities.builder()
                        .roots(true)
                        .build())
                .build();

        client.initialize();
        return client;
    }

    public static JSONArray getMcpToolsFromMultiple(ChatCompletionBase connection) {
        if (!mcpToolsLoaded) {
            mcpToolsArrayByServer = new JSONArray();
            mcpTools = new JSONArray();

            Map<String, String> mcpServers = connection.getMcpSseServers();
            String httpPattern = "^https?://.*";

            for (Map.Entry<String, String> entry : mcpServers.entrySet()) {
                String url = entry.getValue();
                String key = entry.getKey();
                if (url != null && url.matches(httpPattern)) {
                    JSONArray tools = getMcpTools(url);
                    if (tools != null) {
                        for (int i = 0; i < tools.length(); i++) {
                            mcpTools.put(tools.get(i));
                        }

                        JSONObject mcpServerInfo = new JSONObject();
                        mcpServerInfo.put("serverUrl", url);
                        mcpServerInfo.put("serverName", key);
                        mcpServerInfo.put("serverTools", tools);

                        boolean exists = false;
                        for (int i = 0; i < mcpToolsArrayByServer.length(); i++) {
                            JSONObject existing = mcpToolsArrayByServer.getJSONObject(i);
                            if (existing.getString("serverUrl").equals(url)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            mcpToolsArrayByServer.put(mcpServerInfo);
                        }
                    }
                }
            }
            mcpToolsLoaded = true;
        }
        return mcpTools;
    }

    public static JSONArray getMcpToolsFromMultiple(TextGenerationConnection connection) {
        if (!mcpToolsLoaded) {
            mcpToolsArrayByServer = new JSONArray();
            mcpTools = new JSONArray();

            Map<String, String> mcpServers = connection.getMcpSseServers();
            String httpPattern = "^https?://.*";

            for (Map.Entry<String, String> entry : mcpServers.entrySet()) {
                String url = entry.getValue();
                String key = entry.getKey();
                if (url != null && url.matches(httpPattern)) {
                    JSONArray tools = getMcpTools(url);
                    if (tools != null) {
                        for (int i = 0; i < tools.length(); i++) {
                            mcpTools.put(tools.get(i));
                        }

                        JSONObject mcpServerInfo = new JSONObject();
                        mcpServerInfo.put("serverUrl", url);
                        mcpServerInfo.put("serverName", key);
                        mcpServerInfo.put("serverTools", tools);

                        boolean exists = false;
                        for (int i = 0; i < mcpToolsArrayByServer.length(); i++) {
                            JSONObject existing = mcpToolsArrayByServer.getJSONObject(i);
                            if (existing.getString("serverUrl").equals(url)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            mcpToolsArrayByServer.put(mcpServerInfo);
                        }
                    }
                }
            }

            mcpToolsLoaded = true;
        }
        return mcpTools;
    }

    public static JSONArray getMcpTools(String mcpServerUrl) {

        McpSyncClient client = establishClientMCP(mcpServerUrl);

        McpSchema.ListToolsResult tools = client.listTools();
        mcpToolsArray = new JSONArray();

        for (McpSchema.Tool tool : tools.tools()) {
            JSONObject functionObj = new JSONObject();
            functionObj.put("name", tool.name());
            functionObj.put("description", tool.description());

            JSONObject parametersObj = new JSONObject();
            parametersObj.put("type", "object");

            JSONObject propertiesObj = new JSONObject();
            if (tool.inputSchema().properties() != null) {
                for (Map.Entry<String, Object> prop : tool.inputSchema().properties().entrySet()) {
                    if (prop.getValue() instanceof Map) {
                        Map<String, Object> propDetails = (Map<String, Object>) prop.getValue();
                        JSONObject propObj = new JSONObject();
                        for (Map.Entry<String, Object> detail : propDetails.entrySet()) {
                            propObj.put(detail.getKey(), detail.getValue());
                        }
                        propertiesObj.put(prop.getKey(), propObj);
                    }
                }
            }

            parametersObj.put("properties", propertiesObj);

            JSONArray requiredArray = new JSONArray();
            for (String key : tool.inputSchema().properties().keySet()) {
                requiredArray.put(key);
            }
            parametersObj.put("required", requiredArray);

            parametersObj.put("additionalProperties",
                    tool.inputSchema().additionalProperties() != null ?
                            tool.inputSchema().additionalProperties() : false);

            functionObj.put("parameters", parametersObj);
            //functionObj.put("strict", true);

            JSONObject toolObj = new JSONObject();
            toolObj.put("type", "function");
            toolObj.put("function", functionObj);

            mcpToolsArray.put(toolObj);
        }

        client.close();

        return mcpToolsArray;
    }



    public static JSONArray executeTools(String apiResponseJson) throws Exception {

        LOGGER.debug("executeTools - Response from the tools server: {}", apiResponseJson);
        JSONArray resultsArray = new JSONArray();


        JSONObject rootObject = new JSONObject(apiResponseJson);

        JSONArray toolsArray = rootObject.getJSONArray("tools");

        if (toolsArray.length() == 0) {
            return resultsArray;
        }

        for (int i = 0; i < toolsArray.length(); i++) {
            JSONObject toolObject = toolsArray.getJSONObject(i);
            JSONObject functionObject = toolObject.getJSONObject("function");
            String functionName = functionObject.getString("name");
            JSONObject argumentsObject = functionObject.getJSONObject("arguments");
            String serverUrl = findServerUrlForTool(mcpToolsArrayByServer, functionName);
            String serverName = findServerNameForTool(mcpToolsArrayByServer, functionName);
            McpSyncClient client = establishClientMCP(serverUrl);

            Map<String, Object> arguments = new HashMap<>();
            for (String key : argumentsObject.keySet()) {
                arguments.put(key, argumentsObject.getString(key));
            }

            McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(functionName, arguments);
            McpSchema.CallToolResult result = client.callTool(request);

            JSONObject contentObj = new JSONObject();
            for (McpSchema.Content content : result.content()) {
                if (content instanceof McpSchema.TextContent textContent) {
                    LOGGER.info("Debugging the exception. TextContent is {} ", textContent.text());
                    if (PayloadUtils.isValidJson(textContent.text())) {
                        contentObj.put("result", new JSONObject(textContent.text()));
                    } else {
                        contentObj.put("result", textContent.text());
                    }
                }
            }

            JSONObject resultObject = new JSONObject();
            resultObject.put("tool", functionName);
            try {
                resultObject.put("result", contentObj.getJSONObject("result"));
            } catch(Exception e) {
                resultObject.put("result", contentObj.getString("result"));
            }
            
            resultObject.put("serverUrl", serverUrl);
            resultObject.put("serverName", serverName);
            resultObject.put("timestamp", Instant.now());

            resultsArray.put(resultObject);
            client.close();

        }


        return resultsArray;
    }


    private static String findServerUrlForTool(JSONArray servers, String toolName) {
        for (int i = 0; i < servers.length(); i++) {
            JSONObject server = servers.getJSONObject(i);
            JSONArray serverTools = server.getJSONArray("serverTools");

            for (int j = 0; j < serverTools.length(); j++) {
                JSONObject serverTool = serverTools.getJSONObject(j);
                String serverToolName = serverTool.getJSONObject("function").getString("name");

                if (toolName.equals(serverToolName)) {
                    return server.getString("serverUrl");
                }
            }
        }
        return null; // Tool not found
    }

    private static String findServerNameForTool(JSONArray servers, String toolName) {
        for (int i = 0; i < servers.length(); i++) {
            JSONObject server = servers.getJSONObject(i);
            JSONArray serverTools = server.getJSONArray("serverTools");

            for (int j = 0; j < serverTools.length(); j++) {
                JSONObject serverTool = serverTools.getJSONObject(j);
                String serverToolName = serverTool.getJSONObject("function").getString("name");

                if (toolName.equals(serverToolName)) {
                    return server.getString("serverName");
                }
            }
        }
        return null; // Tool not found
    }
}