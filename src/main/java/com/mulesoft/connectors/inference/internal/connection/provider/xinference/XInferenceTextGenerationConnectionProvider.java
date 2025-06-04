package com.mulesoft.connectors.inference.internal.connection.provider.xinference;

import com.mulesoft.connectors.inference.internal.connection.parameters.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.TextGenerationConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.xinference.providers.XInferenceTextGenerationModelNameProvider;
import com.mulesoft.connectors.inference.internal.connection.types.xinference.XInferenceTextGenerationConnection;
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

@Alias("xinference")
@DisplayName("XInference")
public class XInferenceTextGenerationConnectionProvider extends TextGenerationConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(XInferenceTextGenerationConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(XInferenceTextGenerationModelNameProvider.class)
    private String xInferenceModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "http://127.0.0.1:9997/v1 or https://inference.top/api/v1")
    @Placement(order = 2)
    @DisplayName("[Xinference] Base URL")
    private String xInferenceUrl;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public XInferenceTextGenerationConnection connect() throws ConnectionException {
        logger.debug("XInferenceTextGenerationConnection connect ...");
            return new XInferenceTextGenerationConnection(getHttpClient(),getObjectMapper(), xInferenceModelName,xInferenceUrl,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(), textGenerationConnectionParameters.getMcpSseServers(),
                    textGenerationConnectionParameters.getTimeout());
    }
}