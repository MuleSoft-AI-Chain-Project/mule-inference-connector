package com.mulesoft.connectors.inference.internal.connection.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

import java.util.Map;

public class TextGenerationConnectionParameters extends BaseConnectionParameters {

  /**
   * This defines the number of LLM Token to be used when generating a response
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "500")
  @Placement(order = 2)
  private Number maxTokens;

  /**
   * This(between 0-2) controls the output randomness. Higher = more random outputs. Lower(closer to 0) = more deterministic
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  @Placement(order = 3)
  private Number temperature;

  /**
   * This specifies the cumulative probability score threshold that the tokens must reach
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  @Placement(order = 4)
  private Number topP;

  /**
   * Mule Inference Connector provides an integrated MCP Client. The MCP Servers must support SSE over HTTP.
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional
  @DisplayName("MCP Server Urls (SSE over HTTP)")
  @NullSafe
  @Placement(order = 5)
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
