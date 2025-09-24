package com.mulesoft.connectors.inference.internal.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

/**
 * Base interface for all request payload DTOs that support additional request attributes. This interface provides common
 * functionality for flattening additional attributes into JSON payloads.
 */
public interface BaseRequestPayloadDTO {

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
