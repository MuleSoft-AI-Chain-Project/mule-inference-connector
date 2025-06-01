package com.mulesoft.connectors.inference.internal.helpers;

import org.json.JSONObject;

import com.mulesoft.connectors.inference.api.metadata.TokenUsage;

public class TokenHelper {

    private static final String INPUT_TOKENS = "input_tokens";
    private static final String OUTPUT_TOKENS = "output_tokens";
    private static final String PROMPT_TOKEN_COUNT = "promptTokenCount";
    private static final String CANDIDATES_TOKEN_COUNT = "candidatesTokenCount";
    private static final String TOTAL_TOKEN_COUNT = "totalTokenCount";

    public static TokenUsage parseUsageFromResponse(String jsonResponse) throws Exception {
        JSONObject root = new JSONObject(jsonResponse);

        int promptTokens = 0;
        int completionTokens = 0;
        int totalTokens = 0;

        if (root.has("usage")) {
            JSONObject usageNode = root.getJSONObject("usage");

            if (usageNode.has("billed_units") && usageNode.has("tokens")) {
                // Handle the case with nested billed_units and tokens objects
                JSONObject billedUnitsNode = usageNode.getJSONObject("billed_units");
                promptTokens = billedUnitsNode.getInt(INPUT_TOKENS);
                completionTokens = billedUnitsNode.getInt(OUTPUT_TOKENS);
                totalTokens = promptTokens + completionTokens;
            } else if (usageNode.has(INPUT_TOKENS) && usageNode.has(OUTPUT_TOKENS)) {
                // Handle the case with direct input_tokens and output_tokens
                promptTokens = usageNode.getInt(INPUT_TOKENS);
                completionTokens = usageNode.getInt(OUTPUT_TOKENS);
                totalTokens = promptTokens + completionTokens;
            } else {
                // Handle the case with prompt_tokens and completion_tokens
                promptTokens = usageNode.getInt("prompt_tokens");
                completionTokens = usageNode.getInt("completion_tokens");
                totalTokens = usageNode.getInt("total_tokens");
            }
        } else if (root.has("usageMetadata")) {
        	//for Vertex AI
            JSONObject usageMetadataNode = root.getJSONObject("usageMetadata");
            
            if (usageMetadataNode.has(PROMPT_TOKEN_COUNT) && !usageMetadataNode.isNull(PROMPT_TOKEN_COUNT)) {
                promptTokens = usageMetadataNode.getInt(PROMPT_TOKEN_COUNT);
            }

            if (usageMetadataNode.has(CANDIDATES_TOKEN_COUNT) && !usageMetadataNode.isNull(CANDIDATES_TOKEN_COUNT)) {
                completionTokens = usageMetadataNode.getInt(CANDIDATES_TOKEN_COUNT);
            }

            if (usageMetadataNode.has(TOTAL_TOKEN_COUNT) && !usageMetadataNode.isNull(TOTAL_TOKEN_COUNT)) {
                totalTokens = usageMetadataNode.getInt(TOTAL_TOKEN_COUNT);
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

    public static TokenUsage parseUsageFromResponse(com.mulesoft.connectors.inference.internal.dto.textgeneration.response.TokenUsage usage) {
        return new TokenUsage(usage.promptTokens(), usage.completionTokens(), usage.totalTokens());
    }
}