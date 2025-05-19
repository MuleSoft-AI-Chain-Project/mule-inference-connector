package com.mulesoft.connectors.inference.internal.api.delegate;

import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.io.IOUtils.toInputStream;

public class Moderation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Moderation.class);
    protected BaseConnection baseConnection;
    private static Moderation instance;

    public Moderation( BaseConnection connection) {
        this.baseConnection = connection;
    }

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

    public String getRequestPayload(InputStream text) {
        String inputString = convertStreamToString(text);
        JSONObject payload = new JSONObject();
        if (isJsonArray(inputString)) {
            JSONArray inputArray = new JSONArray(inputString);
            payload.put("input", inputArray);
        } else {
            JSONObject inputObject = new JSONObject("{ \"prompt\": " + inputString + " }");
            payload.put("input", inputObject.get("prompt"));
        }
        payload.put("model", baseConnection.getModelName());
        return payload.toString();
    }

    public Result<InputStream, LLMResponseAttributes> processResponse(String llmResponse) throws ModuleException {
        try {
            JSONObject responseObject = new JSONObject();
            JSONObject llmResponseObject = new JSONObject(llmResponse);
            responseObject.put("flagged", isFlagged(llmResponseObject));
            List<Map<String, Double>> categories = getCategories(llmResponseObject);
            JSONArray categoriesArray = new JSONArray();
            categories.forEach(category -> categoriesArray.put(new JSONObject(category)));
            responseObject.put("categories", categoriesArray);
            return Result.<InputStream, LLMResponseAttributes>builder()
                    .attributesMediaType(MediaType.APPLICATION_JAVA)
                    .output(toInputStream(responseObject.toString(), StandardCharsets.UTF_8))
                    .mediaType(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error processing moderation response: {}", e.getMessage(), e);
            throw new ModuleException("MODERATION ERROR", InferenceErrorType.TEXT_MODERATION, e);
        }
    }

    private boolean isFlagged(JSONObject response) {

        return switch (baseConnection.getInferenceType())
        {
            case "MistralAI" -> isMistralFlagged(response);
            case "OpenAI" -> isOpenAIFlagged(response);
            default -> throw new IllegalStateException("Unexpected value: " + baseConnection.getInferenceType());
        };
    }

    private boolean isOpenAIFlagged(JSONObject response) {
        JSONArray results = response.getJSONArray("results");
        boolean isFlagged = false;
        for (Object result : results) {
            JSONObject resultObject = (JSONObject) result;
            isFlagged = resultObject.getBoolean("flagged") || isFlagged;
        }
        return isFlagged;
    }

    private boolean isMistralFlagged(JSONObject response) {
        boolean isFlagged = false;
        JSONArray results = response.getJSONArray("results");
        for (Object result : results) {
            JSONObject resultObject = (JSONObject) result;
            JSONObject categories = resultObject.getJSONObject("categories");
            for (String key : categories.keySet()) {
                if (categories.getBoolean(key)) {
                    isFlagged = true;
                    break;
                }
            }
        }
        return isFlagged;
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