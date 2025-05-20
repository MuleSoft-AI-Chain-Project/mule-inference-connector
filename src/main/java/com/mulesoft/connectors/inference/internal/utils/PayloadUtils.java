package com.mulesoft.connectors.inference.internal.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

/**
 * Utility class for payload operations.
 */
public class PayloadUtils {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadUtils.class);

    public static boolean isBase64String(String str) {
        if (str == null || str.length() % 4 != 0 || !str.matches("^[A-Za-z0-9+/]*={0,2}$")) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String getMimeType(String base64String) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
        return mimeType != null ? mimeType : "image/jpeg";
    }
    
    public static String getMimeTypeFromUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return "image/jpeg"; // Default
        }
        imageUrl = imageUrl.toLowerCase().trim();

        if (imageUrl.endsWith(".png")) {
            return "image/png";
        } else if (imageUrl.endsWith(".jpg") || imageUrl.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        // Add more types if needed
        return "image/jpeg"; // Default fallback
    }
    
    public static boolean isValidJson(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (Exception ex1) {
            try {
                new JSONArray(json);
                return true;
            } catch (Exception ex2) {
                return false;
            }
        }
    }
}
