package com.mulesoft.connectors.inference.internal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ParseUtils {

  private static final Logger logger = LoggerFactory.getLogger(ParseUtils.class);

  // Private constructor to prevent instantiation of utility class
  private ParseUtils() {
    throw new UnsupportedOperationException("Utility class should not be instantiated");
  }

  /**
   * Parses the InputStream additionalRequestAttributes into a Map<String, Object>. If the InputStream is null, returns an empty
   * map.
   */
  public static Map<String, Object> parseAdditionalRequestAttributes(InputStream additionalRequestAttributes,
                                                                     ObjectMapper objectMapper)
      throws IOException {
    if (additionalRequestAttributes == null) {
      logger.debug("Additional request attributes is null, returning empty map");
      return Map.of();
    }

    // Parse the InputStream as JSON into a Map<String, Object>
    Map<String, Object> parsedMap = objectMapper.readValue(additionalRequestAttributes,
                                                           objectMapper.getTypeFactory()
                                                               .constructMapType(Map.class, String.class, Object.class));
    return parsedMap != null ? parsedMap : Map.of();
  }

}
