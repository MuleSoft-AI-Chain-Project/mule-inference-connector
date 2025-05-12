package com.mulesoft.connectors.internal.api.delegate;

import com.mulesoft.connectors.internal.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.internal.config.ModerationConfig;
import com.mulesoft.connectors.internal.connection.BaseConnection;
import com.mulesoft.connectors.internal.connection.types.ModerationBase;
import com.mulesoft.connectors.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.internal.utils.ConnectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

public abstract class Moderation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Moderation.class);
    protected final ModerationConfig configuration;
    protected ModerationBase connection; // Use provided Moderation connection
    protected BaseConnection baseConnection;
    private static Moderation instance;

    protected Moderation(ModerationConfig configuration, ModerationBase connection) {
        this.configuration = configuration;
        this.connection = connection;
    }
    protected Moderation(ModerationConfig configuration, BaseConnection connection) {
        this.configuration = configuration;
        this.baseConnection = connection;
    }

    @Deprecated
    public static Moderation getInstance(ModerationConfig configuration, ModerationBase connection) {
        if (instance == null) {
            instance = findInstance(configuration, connection);
        }
        if (instance == null) {
            throw new IllegalArgumentException("No moderation provider found for the given configuration");
        }
        return instance;
    }

    public static Moderation getInstance(BaseConnection connection) {
        if (instance == null) {
            instance = findInstance(connection);
        }
        if (instance == null) {
            throw new IllegalArgumentException("No moderation provider found for the given configuration");
        }
        return instance;
    }

    private static Moderation findInstance(ModerationConfig configuration, ModerationBase connection) {
        switch (connection.getInferenceType()) {
            case "MISTRAL_AI":
                return new MistralAIModeration(configuration, connection);
            case "OPENAI":
                return new OpenAIModeration(configuration, connection);
            default:
                return null;
        }
    }

    private static Moderation findInstance(BaseConnection connection) {

        switch (connection.getInferenceType()) {
            case "MistralAI":
                return new MistralAIModeration(connection);
            case "OpenAI":
                return new OpenAIModeration(connection);
            default:
                return null;
        }
    }

    public Result<InputStream, LLMResponseAttributes> moderate(InputStream text) {
        String payload = this.getRequestPayload(text, null);
        LOGGER.debug("Moderation payload that will be sent to the LLM {}", payload);
        String response = this.getResponsePayload(payload);
        LOGGER.debug("Moderation service - response from LLM: {}", response);
        return this.processResponse(response);
    }

    protected abstract JSONObject handleModelSpecificRequestPayload(JSONObject payload, Object text, Object images);

    protected abstract String getTextInputAttributeName();

    public abstract String getAPIUrl();

    protected abstract boolean isFlagged(JSONObject response);

    protected List<Map<String, Double>> getCategories(JSONObject llmResponseObject) {
        JSONArray results = llmResponseObject.getJSONArray("results");
        List<Map<String, Double>> returnValueList = new ArrayList<>();
        for (Object result : results) {
            Map<String, Double> categoriesMap = new HashMap<>();
            JSONObject resultObject = (JSONObject) result;
            JSONObject categoriesObject = resultObject.getJSONObject("categories");
            JSONObject categoryScoresObject = resultObject.getJSONObject("category_scores");
            for (String key : categoriesObject.keySet()) {
                categoriesMap.put(key, categoryScoresObject.getDouble(key));
            }
            returnValueList.add(categoriesMap);
        }
        return returnValueList;
    }

    protected String getRequestPayload(InputStream text, InputStream images) {
        String inputString = convertStreamToString(text);
        JSONObject payload = new JSONObject();
        if (isJsonArray(inputString)) {
            JSONArray inputArray = new JSONArray(inputString);
            payload.put(getTextInputAttributeName(), inputArray);
        } else {
            JSONObject inputObject = new JSONObject("{ \"prompt\": " + inputString + " }");
            payload.put(getTextInputAttributeName(), inputObject.get("prompt"));
        }
        payload = handleModelSpecificRequestPayload(payload, text, images);
        return payload.toString();
    }

    protected String getResponsePayload(String requestPayload) {
        try {
            URL moderationURL = new URL(this.getAPIUrl());
            if (baseConnection == null) {
                throw new IllegalStateException("Moderation connection is not initialized");
            }
            return ConnectionUtils.executeREST(moderationURL, baseConnection, requestPayload);
        } catch (Exception e) {
            LOGGER.error("Error in moderation: {}", e.getMessage(), e);
            throw new ModuleException("MODERATION ERROR", InferenceErrorType.TEXT_MODERATION, e);
        }
    }

    protected Result<InputStream, LLMResponseAttributes> processResponse(String llmResponse) throws ModuleException {
        try {
            JSONObject responseObject = new JSONObject();
            JSONObject llmResponseObject = new JSONObject(llmResponse);
            responseObject.put("flagged", isFlagged(llmResponseObject));
            List<Map<String, Double>> categories = getCategories(llmResponseObject);
            JSONArray categoriesArray = new JSONArray();
            categories.forEach(category -> categoriesArray.put(new JSONObject(category)));
            responseObject.put("categories", categoriesArray);
            return Result.<InputStream, LLMResponseAttributes>builder()
                    .attributesMediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JAVA)
                    .output(toInputStream(responseObject.toString(), StandardCharsets.UTF_8))
                    .mediaType(org.mule.runtime.api.metadata.MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error processing moderation response: {}", e.getMessage(), e);
            throw new ModuleException("MODERATION ERROR", InferenceErrorType.TEXT_MODERATION, e);
        }
    }

    protected static boolean isJsonArray(String input) {
        try {
            new JSONArray(input);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    protected static boolean isValidJson(String input) {
        try {
            new JSONObject(input);
            return true;
        } catch (Exception e) {
            return isJsonArray(input);
        }
    }

    protected static String convertStreamToString(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            LOGGER.error("Error converting stream to string. Returning empty string", e);
            return "";
        }
    }
}