package com.mulesoft.connectors.inference.internal.service;

public interface BaseService {

    default TextGenerationService getTextGenerationServiceInstance() {
        if (this instanceof TextGenerationService textGenerationService)
            return textGenerationService;
        throw new RuntimeException("Text Generation Service not found");
    }

    default ImageGenerationService getImageGenerationServiceInstance() {
        if (this instanceof ImageGenerationService imageGenerationServiceInstance)
            return imageGenerationServiceInstance;
        throw new RuntimeException("Image Generation Service not found");
    }

    default VisionModelService getVisionModelServiceInstance() {
        if (this instanceof VisionModelService visionModelServiceInstance)
            return visionModelServiceInstance;
        throw new RuntimeException("Vision Model Service not found");
    }

    default ModerationService getModerationServiceInstance() {
        if (this instanceof ModerationService moderationServiceInstance)
            return moderationServiceInstance;
        throw new RuntimeException("Moderation Service not found");
    }
}
