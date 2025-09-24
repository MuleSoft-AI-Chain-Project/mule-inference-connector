package com.mulesoft.connectors.inference.internal.dto.textgeneration;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface TextGenerationRequestPayloadDTO {

  /**
   * Gets the additional request attributes map for flattening into the JSON payload. This method should be implemented by records
   * to return their additionalRequestAttributes field.
   */
  Map<String, Object> getAdditionalRequestAttributesMap();

  /**
   * Default implementation that flattens additional request attributes into the JSON payload. This method uses @JsonAnyGetter to
   * merge the additional attributes at the root level.
   *
   * @return Map of additional request attributes for JSON serialization
   */
  @JsonAnyGetter
  default Map<String, Object> getAdditionalRequestAttributes() {
    Map<String, Object> attributes = getAdditionalRequestAttributesMap();
    return attributes != null ? attributes : Map.of();
  }
}
