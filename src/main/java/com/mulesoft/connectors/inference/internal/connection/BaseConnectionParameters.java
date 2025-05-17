package com.mulesoft.connectors.inference.internal.connection;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class BaseConnectionParameters {

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("API Key")
    @Placement(order = 2)
    private String apiKey;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("Timeout (milliseconds)")
    @Optional(defaultValue = "#['600000']")
    private int timeout;

    public String getApiKey() {
        return apiKey;
    }

    public int getTimeout() {
        return timeout;
    }
}
