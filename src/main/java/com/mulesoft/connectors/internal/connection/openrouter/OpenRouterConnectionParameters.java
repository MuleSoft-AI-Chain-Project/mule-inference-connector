package com.mulesoft.connectors.internal.connection.openrouter;

import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class OpenRouterConnectionParameters extends TextGenerationConnectionParameters {

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(OpenRouterModelNameProvider.class)
    private String openRouterModelName;

    public String getOpenRouterModelName() {
        return openRouterModelName;
    }
}
