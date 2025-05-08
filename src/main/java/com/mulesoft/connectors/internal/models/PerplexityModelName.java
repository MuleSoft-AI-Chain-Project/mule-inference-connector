package com.mulesoft.connectors.internal.models;

enum PerplexityModelName {

    sonar("sonar"),
    sonar_pro("sonar-pro"),
    sonar_reasoning("sonar-reasoning"),
    sonar_reasoning_pro("sonar-reasoning-pro"),
    sonar_deep_research("sonar-deep-research");

    private final String value;

    PerplexityModelName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
