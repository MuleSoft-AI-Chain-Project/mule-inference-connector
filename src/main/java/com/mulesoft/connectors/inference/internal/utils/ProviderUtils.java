package com.mulesoft.connectors.inference.internal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for provider-specific operations.
 */
public class ProviderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProviderUtils.class);
    public static final String GOOGLE_PROVIDER_TYPE = "Google";
    public static final String ANTHROPIC_PROVIDER_TYPE = "Anthropic";
    public static final String META_PROVIDER_TYPE = "Meta";

    //get the providers based on the models
    public static String getProviderByModel(String modelName) {
        logger.debug("model name {}", modelName);

        if (modelName == null || modelName.isEmpty()) {
            return "Unknown";
        }
        String upperName = modelName.toUpperCase();
        
        if (upperName.startsWith("GEMINI")) {
            return GOOGLE_PROVIDER_TYPE;
        } else if (upperName.startsWith("CLAUDE")) {
            return ANTHROPIC_PROVIDER_TYPE;
        } else if (upperName.startsWith("META")) {
            return META_PROVIDER_TYPE;
        } else {
            return "Unknown";
        }
    }
}