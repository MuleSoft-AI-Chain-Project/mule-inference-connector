package com.mulesoft.connectors.inference.internal.connection.vertexai.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.vertexai.VertexAIExpressVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.vertexai.providers.VertexAIExpressVisionModelNameProvider;
import org.mule.runtime.api.connection.ConnectionException;
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

@Alias("vertexai-express-vision")
@DisplayName("Vertex AI Express")
public class VertexAIExpressVisionConnectionProvider extends VisionModelConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(VertexAIExpressVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(VertexAIExpressVisionModelNameProvider.class)
    private String vertexAIExpressModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public VertexAIExpressVisionConnection connect() throws ConnectionException {
        logger.debug("VertexAIExpressVisionConnection connect ...");
        return new VertexAIExpressVisionConnection(getHttpClient(),getObjectMapper(), vertexAIExpressModelName,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(),
                textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(),
                textGenerationConnectionParameters.getTimeout());
    }
}