package com.mulesoft.connectors.inference.internal.connection.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class VisionConnectionParameters extends BaseConnectionParameters{

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "500")
    @Placement(order = 2)
    private Number maxTokens;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "0.9")
    @Placement(order = 3)
    private Number temperature;
    @Parameter

    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "0.9")
    @Placement(order = 4)
    private Number topP;

    public Number getMaxTokens() {
        return maxTokens;
    }

    public Number getTemperature() {
        return temperature;
    }

    public Number getTopP() {
        return topP;
    }
}
