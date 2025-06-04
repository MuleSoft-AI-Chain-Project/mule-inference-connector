package com.mulesoft.connectors.inference.internal.connection;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import java.util.concurrent.TimeUnit;

import static org.mule.runtime.extension.api.annotation.param.display.Placement.ADVANCED_TAB;

public class BaseConnectionParameters {

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("API Key")
    @Placement(order = 1)
    private String apiKey;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("Timeout")
    @Placement(tab = ADVANCED_TAB, order = 1)
    @Optional(defaultValue = "60")
    private int timeout;

    @Parameter
    @Optional(defaultValue = "SECONDS")
    @Placement(tab = ADVANCED_TAB, order = 2)
    private TimeUnit timeoutUnit;

    public String getApiKey() {
        return apiKey;
    }

    public int getTimeout() {
        return Math.toIntExact(getTimeoutUnit().toMillis(timeout));
    }

    public TimeUnit getTimeoutUnit() {
        return timeoutUnit;
    }
}
