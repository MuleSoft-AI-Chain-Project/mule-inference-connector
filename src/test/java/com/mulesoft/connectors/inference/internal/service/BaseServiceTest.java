package com.mulesoft.connectors.inference.internal.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mule.runtime.extension.api.exception.ModuleException;

import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

class BaseServiceTest {

    @Test
    void testGetTextGenerationServiceInstanceThrowsException() {
        BaseService baseService = new TestBaseServiceImpl();
        
        ModuleException exception = assertThrows(ModuleException.class,
                baseService::getTextGenerationServiceInstance);
        
        assertEquals("Text Generation Service not found", exception.getMessage());
        assertEquals(InferenceErrorType.CHAT_OPERATION_FAILURE, exception.getType());
    }

    @Test
    void testGetImageGenerationServiceInstanceThrowsException() {
        BaseService baseService = new TestBaseServiceImpl();
        
        ModuleException exception = assertThrows(ModuleException.class,
                baseService::getImageGenerationServiceInstance);
        
        assertEquals("Image Generation Service not found", exception.getMessage());
        assertEquals(InferenceErrorType.IMAGE_GENERATION_FAILURE, exception.getType());
    }

    @Test
    void testGetVisionModelServiceInstanceThrowsException() {
        BaseService baseService = new TestBaseServiceImpl();
        
        ModuleException exception = assertThrows(ModuleException.class,
                baseService::getVisionModelServiceInstance);
        
        assertEquals("Vision Model Service not found", exception.getMessage());
        assertEquals(InferenceErrorType.READ_IMAGE_OPERATION_FAILURE, exception.getType());
    }

    @Test
    void testGetModerationServiceInstanceThrowsException() {
        BaseService baseService = new TestBaseServiceImpl();
        
        ModuleException exception = assertThrows(ModuleException.class, 
            () -> baseService.getModerationServiceInstance());
        
        assertEquals("Moderation Service not found", exception.getMessage());
        assertEquals(InferenceErrorType.TOXICITY_DETECTION_OPERATION_FAILURE, exception.getType());
    }

    /**
     * Test implementation of BaseService that doesn't implement any specific service interfaces.
     * This ensures all the default methods will throw exceptions.
     */
    private static class TestBaseServiceImpl implements BaseService {
        // Empty implementation - doesn't implement any specific service interfaces
    }
} 