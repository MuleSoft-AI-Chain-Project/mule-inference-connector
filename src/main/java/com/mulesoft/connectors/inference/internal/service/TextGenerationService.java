package com.mulesoft.connectors.inference.internal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mulesoft.connectors.inference.api.metadata.AdditionalAttributes;
import com.mulesoft.connectors.inference.api.metadata.LLMResponseAttributes;
import com.mulesoft.connectors.inference.api.response.TextGenerationResponse;
import com.mulesoft.connectors.inference.internal.connection.TextGenerationConnection;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.TextGenerationRequestPayloadDTO;
import com.mulesoft.connectors.inference.internal.dto.textgeneration.response.ChatCompletionResponse;
import com.mulesoft.connectors.inference.internal.exception.InferenceErrorType;
import com.mulesoft.connectors.inference.internal.helpers.ResponseHelper;
import com.mulesoft.connectors.inference.internal.helpers.TokenHelper;
import com.mulesoft.connectors.inference.internal.helpers.payload.RequestPayloadHelper;
import com.mulesoft.connectors.inference.internal.utils.ConnectionUtils;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

public class TextGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(TextGenerationService.class);

    private final RequestPayloadHelper payloadHelper;
    private final ObjectMapper objectMapper;

    public TextGenerationService(RequestPayloadHelper requestPayloadHelper, ObjectMapper objectMapper) {
        this.payloadHelper = requestPayloadHelper;
        this.objectMapper = objectMapper;
    }

    public Result<InputStream, LLMResponseAttributes> executeChatAnswerPrompt(TextGenerationConnection connection, String prompt) {
        try {
            TextGenerationRequestPayloadDTO requestPayloadDTO = payloadHelper.buildChatAnswerPromptPayload(connection,prompt);

            var response = ConnectionUtils.executeChatRestRequest(connection,
                    connection.getApiURL(), requestPayloadDTO);

            ChatCompletionResponse chatResponse = connection.getResponseHandler()
                    .processChatResponse(response);
            logger.debug("Chat answer prompt response: {}",chatResponse.toString());

            var chatRespOutput = chatResponse.choices().get(0);

            return ResponseHelper.createLLMResponse(
                    objectMapper.writeValueAsString(new TextGenerationResponse(chatRespOutput.message().content())),
                    TokenHelper.parseUsageFromResponse(chatResponse.usage()),
                    new AdditionalAttributes(chatResponse.id(), chatResponse.model(), chatRespOutput.finishReason()));

        } catch (IOException | TimeoutException e) {
            throw new ModuleException("Error in executing chat answer prompt",
                    InferenceErrorType.CHAT_COMPLETION_FAILURE, e);
        }
    }
}
