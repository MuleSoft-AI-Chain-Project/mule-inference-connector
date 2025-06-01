package com.mulesoft.connectors.inference.internal.connection.provider.ollama;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.provider.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.llmmodels.ollama.providers.OllamaVisionModelNameProvider;
import com.mulesoft.connectors.inference.internal.connection.ollama.OllamaVisionConnection;
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

@Alias("ollama-vision")
@DisplayName("Ollama")
public class OllamaVisionConnectionProvider extends VisionModelConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(OllamaVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OllamaVisionModelNameProvider.class)
    private String ollamaModelName;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "http://localhost:11434/api")
    @Placement(tab = "Additional Properties")
    @DisplayName("[Ollama] Base URL")
    private String ollamaUrl;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public OllamaVisionConnection connect() throws ConnectionException {
        logger.debug("OllamaVisionConnection connect ...");
            return new OllamaVisionConnection(getHttpClient(),getObjectMapper(), ollamaModelName, ollamaUrl,
                    textGenerationConnectionParameters.getApiKey(),
                    textGenerationConnectionParameters.getTemperature(), textGenerationConnectionParameters.getTopP(),
                    textGenerationConnectionParameters.getMaxTokens(),
                    textGenerationConnectionParameters.getTimeout());
    }
}