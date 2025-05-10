package com.mulesoft.connectors.internal.connection.openrouter;

import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class OpenRouterConnectionParameters extends TextGenerationConnectionParameters {

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenRouterModelNameProvider.class)
    private String openRouterModelName;

    @Parameter
    @Placement(order = 2)
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("API Key")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public String getOpenRouterModelName() {
        return openRouterModelName;
    }
}
