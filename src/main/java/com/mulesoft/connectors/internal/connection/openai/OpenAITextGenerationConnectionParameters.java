package com.mulesoft.connectors.internal.connection.openai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import com.mulesoft.connectors.internal.models.openai.providers.OpenAITextGenerationModelNameProvider;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class OpenAITextGenerationConnectionParameters extends TextGenerationConnectionParameters {

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenAITextGenerationModelNameProvider.class)
    @Optional(defaultValue = "gpt-3.5-turbo")
    private String openAIModelName;

    public String getOpenAIModelName() {
        return openAIModelName;
    }
}
