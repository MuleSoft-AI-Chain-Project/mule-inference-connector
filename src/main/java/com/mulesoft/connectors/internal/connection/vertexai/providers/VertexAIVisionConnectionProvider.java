package com.mulesoft.connectors.internal.connection.vertexai.providers;

import com.mulesoft.connectors.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.internal.connection.TextGenerationConnectionProvider;
import com.mulesoft.connectors.internal.connection.vertexai.VertexAIVisionConnection;
import com.mulesoft.connectors.internal.models.vertexai.providers.VertexAIVisionModelNameProvider;
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

import java.net.MalformedURLException;

@Alias("vertexai-vision")
@DisplayName("Vertex AI")
public class VertexAIVisionConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(VertexAIVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(VertexAIVisionModelNameProvider.class)
    private String vertexAIModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public VertexAIVisionConnection connect() throws ConnectionException {
        logger.debug("VertexAIVisionConnection connect ...");
        try {
            return new VertexAIVisionConnection(httpClient, vertexAIModelName,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(),
                    textGenerationConnectionParameters.getTimeout());
        } catch (MalformedURLException e) {
            throw new ConnectionException("Invalid Vertex AI URL", e);
        }
    }

    @Override
    public void disconnect(TextGenerationConnection baseConnection) {
        logger.debug("VertexAIVisionConnection disconnected ...");
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