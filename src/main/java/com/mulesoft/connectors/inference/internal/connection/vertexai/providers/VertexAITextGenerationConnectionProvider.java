package com.mulesoft.connectors.inference.internal.connection.vertexai.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.VertexAITextGenerationConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers.VertexAITextGenerationModelNameProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.Expression;
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

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public VertexAITextGenerationConnection connect() throws ConnectionException {
        logger.debug("VertexAITextGenerationConnection connect ...");
        return new VertexAITextGenerationConnection(getHttpClient(),getObjectMapper(), vertexAIModelName,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                textGenerationConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("VertexAITextGenerationConnection disconnected ...");
    }

    @Override
    public ConnectionValidationResult validate(TextGenerationConnection baseConnection) {
        logger.debug("Validating connection... ");
        try {
            return ConnectionValidationResult.success();
        } catch (Exception e) {
            return ConnectionValidationResult.failure("Failed to validate connection to Vertex AI", e);
        }
    }
} 