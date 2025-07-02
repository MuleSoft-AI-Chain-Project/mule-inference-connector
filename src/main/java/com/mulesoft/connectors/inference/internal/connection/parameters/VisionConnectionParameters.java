package com.mulesoft.connectors.inference.internal.connection.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;

public class VisionConnectionParameters extends BaseConnectionParameters {

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
