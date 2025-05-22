package com.mulesoft.connectors.inference.internal.connection.huggingface.providers;

import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnection;
import com.mulesoft.connectors.inference.internal.connection.VisionModelConnectionProvider;
import com.mulesoft.connectors.inference.internal.connection.huggingface.HuggingFaceVisionConnection;
import com.mulesoft.connectors.inference.internal.llmmodels.huggingface.providers.HuggingFaceVisionModelNameProvider;
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

@Alias("hugging-face-vision")
@DisplayName("Hugging Face")
public class HuggingFaceVisionConnectionProvider extends VisionModelConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(HuggingFaceVisionConnectionProvider.class);

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(HuggingFaceVisionModelNameProvider.class)
    private String huggingFaceModelName;

    @ParameterGroup(name = Placement.CONNECTION_TAB)
    private TextGenerationConnectionParameters textGenerationConnectionParameters;

    @Override
    public HuggingFaceVisionConnection connect() throws ConnectionException {
        logger.debug("HuggingFaceVisionConnection connect ...");
        return new HuggingFaceVisionConnection(getHttpClient(),getObjectMapper(), huggingFaceModelName,
                textGenerationConnectionParameters.getApiKey(),
                textGenerationConnectionParameters.getTemperature(),
                textGenerationConnectionParameters.getTopP(),
                textGenerationConnectionParameters.getMaxTokens(),
                textGenerationConnectionParameters.getTimeout());
    }

    @Override
    public void disconnect(VisionModelConnection baseConnection) {
        logger.debug("HuggingFaceVisionConnection disconnected ...");
    }

    @Override
    public ConnectionValidationResult validate(VisionModelConnection baseConnection) {
        logger.debug("Validating connection... ");
        try {
            return ConnectionValidationResult.success();
        } catch (Exception e) {
            return ConnectionValidationResult.failure("Failed to validate connection to HuggingFace", e);
        }
    }
} 