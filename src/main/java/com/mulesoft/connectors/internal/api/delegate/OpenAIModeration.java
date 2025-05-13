package com.mulesoft.connectors.internal.api.delegate;

import com.mulesoft.connectors.internal.connection.BaseConnection;
import com.mulesoft.connectors.internal.constants.InferenceConstants;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenAIModeration extends Moderation {

    String modelName ;

    public OpenAIModeration(BaseConnection connection) {
        super(null,connection);
        modelName = connection.getModelName();
    }

    @Override
    public String getAPIUrl() {
        return InferenceConstants.OPEN_AI_URL + InferenceConstants.MODERATIONS_PATH;
    }

    @Override
    protected JSONObject handleModelSpecificRequestPayload(JSONObject payload, Object text, Object images) {
        payload.put("model", modelName);
        return payload;
    }

    @Override
    protected String getTextInputAttributeName() {
        return "input";
    }

    @Override
    protected boolean isFlagged(JSONObject llmResponseObject) {
        JSONArray results = llmResponseObject.getJSONArray("results");
        boolean isFlagged = false;
        for (Object result : results) {
            JSONObject resultObject = (JSONObject) result;
            isFlagged = resultObject.getBoolean("flagged") || isFlagged;
        }
        return isFlagged;
    }
}