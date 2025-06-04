package com.mulesoft.connectors.inference.internal.service;

import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;
import org.mule.runtime.extension.api.exception.ModuleException;

public interface BaseService {

    default TextGenerationService getTextGenerationServiceInstance() {
        if (this instanceof TextGenerationService textGenerationService)
            return textGenerationService;
        throw new ModuleException("Text Generation Service not found", InferenceErrorType.CHAT_OPERATION_FAILURE);
    }

    default ImageGenerationService getImageGenerationServiceInstance() {
        if (this instanceof ImageGenerationService imageGenerationServiceInstance)
            return imageGenerationServiceInstance;
        throw new ModuleException("Image Generation Service not found", InferenceErrorType.IMAGE_GENERATION_FAILURE);
    }

    default VisionModelService getVisionModelServiceInstance() {
        if (this instanceof VisionModelService visionModelServiceInstance)
            return visionModelServiceInstance;
        throw new ModuleException("Vision Model Service not found", InferenceErrorType.READ_IMAGE_OPERATION_FAILURE);
    }

    default ModerationService getModerationServiceInstance() {
        if (this instanceof ModerationService moderationServiceInstance)
            return moderationServiceInstance;
        throw new ModuleException("Moderation Service not found", InferenceErrorType.TOXICITY_DETECTION_OPERATION_FAILURE);
    }
}
