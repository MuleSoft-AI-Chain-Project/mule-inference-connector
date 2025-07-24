package com.mulesoft.connectors.inference.internal.connection.parameters;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

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
   * (0-2), controls the output randomness. Higher = more random outputs. Lower(closer to 0) = more deterministic
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  @Placement(order = 3)
  @Summary("Controls randomness; low is predictable, high is random")
  private Number temperature;

  /**
   * Controls diversity by creating a nucleus of the most probable words to choose from for the next token. This specifies the
   * cumulative probability score threshold that the tokens must reach
   */
  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  @Optional(defaultValue = "0.9")
  @Placement(order = 4)
  @Summary("Controls diversity by limiting choices to the most probable options.")
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
