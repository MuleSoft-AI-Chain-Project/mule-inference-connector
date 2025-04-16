package com.mulesoft.connectors.internal.api.delegate;

import com.mulesoft.connectors.internal.config.ModerationConfig;
import com.mulesoft.connectors.internal.connection.types.ModerationBase;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class MistralAIModeration extends Moderation {
    protected MistralAIModeration(ModerationConfig configuration, ModerationBase connection) {
        super(configuration, connection);
    }

    @Override
    public String getAPIUrl() {
        return InferenceConstants.MISTRAL_AI_URL + InferenceConstants.MODERATIONS_PATH;
    }

    @Override
    protected JSONObject handleModelSpecificRequestPayload(JSONObject payload, Object text, Object images) {
        payload.put("model", connection.getModelName());
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