package com.mulesoft.connectors.internal.connection;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.util.Map;

public class TextGenerationConnectionParameters extends BaseConnectionParameters{

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "500")
    private Number maxTokens;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "0.9")
    private Number temperature;
    @Parameter

    @Expression(ExpressionSupport.SUPPORTED)
    @Optional(defaultValue = "0.9")
    private Number topP;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @Optional
    @DisplayName("MCP Server Urls (SSE over HTTP)")
    private Map<String, String> mcpSseServers;

    public Number getMaxTokens() {
        return maxTokens;
    }

    public Number getTemperature() {
        return temperature;
    }

    public Number getTopP() {
        return topP;
    }

    public Map<String, String> getMcpSseServers() {
        return mcpSseServers;
    }
}
