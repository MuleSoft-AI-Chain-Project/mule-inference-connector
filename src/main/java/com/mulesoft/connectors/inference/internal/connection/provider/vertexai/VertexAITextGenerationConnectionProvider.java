package com.mulesoft.connectors.inference.internal.connection.provider.vertexai;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers.VertexAITextGenerationModelNameProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.VertexAITextGenerationConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Alias("vertexai")
@DisplayName("Vertex AI")
public class VertexAITextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(VertexAITextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(VertexAITextGenerationModelNameProvider.class)
    private String vertexAIModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "us-central1")
    @DisplayName("[VertexAI] Location Id")
    @Placement(order = 2)
    private String vertexAILocationId;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[VertexAI] Project Id")
    @Placement(order = 3)
    private String vertexAIProjectId;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("[VertexAI] Service Account Key")
    @Placement(order = 4)
    private String vertexAIServiceAccountKey;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public VertexAITextGenerationConnection connect() throws ConnectionException {
        logger.debug("VertexAITextGenerationConnection connect ...");
        return new VertexAITextGenerationConnection(getHttpClient(),getObjectMapper(), vertexAIModelName,
                vertexAILocationId, vertexAIProjectId,vertexAIServiceAccountKey,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                textGenerationConnectionParameters.getTimeout());
    }
}