package com.mulesoft.connectors.internal.helpers;

import org.json.JSONObject;

import com.mulesoft.connectors.internal.api.metadata.TokenUsage;

public class TokenHelper {

    public static TokenUsage parseUsageFromResponse(String jsonResponse) throws Exception {
        JSONObject root = new JSONObject(jsonResponse);

        int promptTokens;
        int completionTokens;
        int totalTokens;

        if (root.has("usage")) {
            JSONObject usageNode = root.getJSONObject("usage");

            if (usageNode.has("billed_units") && usageNode.has("tokens")) {
                // Handle the case with nested billed_units and tokens objects
                JSONObject billedUnitsNode = usageNode.getJSONObject("billed_units");
                promptTokens = billedUnitsNode.getInt("input_tokens");
                completionTokens = billedUnitsNode.getInt("output_tokens");
                totalTokens = promptTokens + completionTokens;
            } else if (usageNode.has("input_tokens") && usageNode.has("output_tokens")) {
                // Handle the case with direct input_tokens and output_tokens
                promptTokens = usageNode.getInt("input_tokens");
                completionTokens = usageNode.getInt("output_tokens");
                totalTokens = promptTokens + completionTokens;
            } else {
                // Handle the case with prompt_tokens and completion_tokens
                promptTokens = usageNode.getInt("prompt_tokens");
                completionTokens = usageNode.getInt("completion_tokens");
                totalTokens = usageNode.getInt("total_tokens");
            }
        } else {
            // Handle the case without a usage object
            promptTokens = root.getInt("prompt_eval_count");
            completionTokens = root.getInt("eval_count");
            totalTokens = promptTokens + completionTokens;
        }

        // Create and populate the Usage object
        TokenUsage usage = new TokenUsage(promptTokens, completionTokens, totalTokens);

        return usage;
    }
}