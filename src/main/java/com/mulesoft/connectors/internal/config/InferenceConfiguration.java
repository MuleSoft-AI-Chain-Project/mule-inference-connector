package com.mulesoft.connectors.internal.config;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.values.OfValues;

import com.mulesoft.connectors.internal.models.ModelNameProvider;
import com.mulesoft.connectors.internal.models.ModelTypeProvider;
import com.mulesoft.connectors.internal.operations.InferenceOperations;

@Operations(InferenceOperations.class)
public class InferenceConfiguration {

    @Parameter
    @Placement(order = 1, tab = Placement.DEFAULT_TAB)
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("Inference Type")
    @OfValues(ModelTypeProvider.class)
    private String inferenceType;

    @Parameter
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("API Key")
    private String apiKey;

    @Parameter
	@Placement(order = 1, tab = "Inference Parameters")
    @Optional(defaultValue = "Valid-for-portkey-only")
    @Expression(ExpressionSupport.SUPPORTED)
    @DisplayName("Virtual Key")
    private String virtualKey;

    public String getVirtualKey() {
        return virtualKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getInferenceType() {
        return inferenceType;
    }

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@OfValues(ModelNameProvider.class)
	@Optional(defaultValue = "gpt-3.5-turbo")
	private String modelName;

	public String getModelName() {
		return modelName;
	}

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "500")
	private Number maxTokens;

	public Number getMaxTokens() {
		return maxTokens;
	}

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "0.9")
	private Number temperature;

	public Number getTemperature() {
		return temperature;
	}

	@Parameter
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "0.9")
	private Number topP;

	public Number getTopP() {
		return topP;
	}

    @Parameter
	@Placement(order = 2, tab = "Inference Parameters")
	@Expression(ExpressionSupport.SUPPORTED)
	@Optional(defaultValue = "http://localhost:11434/api")
    @DisplayName("Ollama Base URL")
	private String ollamaUrl;

	public String getOllamaUrl() {
		return ollamaUrl;
	}

}





