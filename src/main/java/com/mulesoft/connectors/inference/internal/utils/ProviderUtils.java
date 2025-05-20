package com.mulesoft.connectors.inference.internal.utils;

import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.connection.ChatCompletionBase;
import com.mulesoft.connectors.inference.internal.connection.ModerationImageGenerationBase;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.mule.runtime.http.api.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utility class for provider-specific operations.
 */
public class ProviderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProviderUtils.class);
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
     * @param connection the connector connection
     * @return true if the inference type is OLLAMA, false otherwise
     */
    public static boolean isHuggingFace(BaseConnection connection) {
        return "HUGGING_FACE".equals(connection.getInferenceType());
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
        logger.debug("model name {}", modelName);

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
    public static boolean isOpenAI(BaseConnection configuration) {
        return "OPENAI".equals(configuration.getInferenceType());

    }


    public static boolean isStabilityAI(BaseConnection connection) {
        return "STABILITY_AI".equals(connection.getInferenceType());
    }

    public static boolean isXAI(BaseConnection configuration) {
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
}