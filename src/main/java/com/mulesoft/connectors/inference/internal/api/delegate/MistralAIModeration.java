package com.mulesoft.connectors.inference.internal.api.delegate;

import com.mulesoft.connectors.inference.internal.connection.BaseConnection;
import com.mulesoft.connectors.inference.internal.constants.InferenceConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class MistralAIModeration extends Moderation {
    String modelName ;


    public MistralAIModeration(BaseConnection connection) {
        super(null,connection);
        modelName = connection.getModelName();
    }

    @Override
    public String getAPIUrl() {
        return InferenceConstants.MISTRAL_AI_URL + InferenceConstants.MODERATIONS_PATH;
    }

    @Override
    protected JSONObject handleModelSpecificRequestPayload(JSONObject payload, Object text, Object images) {
        payload.put("model", baseConnection.getModelName());
        return payload;
    }

    @Override
    protected String getTextInputAttributeName() {
        return "input";
    }

    @Override
    protected boolean isFlagged(JSONObject response) {
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
}