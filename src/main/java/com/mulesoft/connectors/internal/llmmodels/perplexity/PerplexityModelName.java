package com.mulesoft.connectors.internal.llmmodels.perplexity;

public enum PerplexityModelName {

    SONAR("sonar", true),
    SONAR_PRO("sonar-pro", true),
    SONAR_REASONING("sonar-reasoning", true),
    SONAR_REASONING_PRO("sonar-reasoning-pro", true),
    SONAR_DEEP_RESEARCH("sonar-deep-research", true);

    private final String value;
    private final boolean textGenerationSupport;

    PerplexityModelName(String value, boolean textGenerationSupport) {
        this.value = value;
        this.textGenerationSupport = textGenerationSupport;
    }

    public boolean isTextGenerationSupport() {
        return textGenerationSupport;
    }

    @Override
    public String toString() {
        return this.value;
    }
}