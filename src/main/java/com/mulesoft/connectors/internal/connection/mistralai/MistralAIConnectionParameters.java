package com.mulesoft.connectors.internal.connection.mistralai;

import com.mulesoft.connectors.internal.connection.TextGenerationConnectionParameters;
import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

public class MistralAIConnectionParameters extends TextGenerationConnectionParameters {

    @Parameter
    @Placement(order = 1)
    @Expression(ExpressionSupport.SUPPORTED)
    @OfValues(MistralAIModelNameProvider.class)
    private String mistralAIModelName;

    public String getMistralAIModelName() {
        return mistralAIModelName;
    }
}
