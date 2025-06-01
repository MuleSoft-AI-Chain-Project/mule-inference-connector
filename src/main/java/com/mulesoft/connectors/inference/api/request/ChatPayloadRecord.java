package com.mulesoft.connectors.inference.api.request;

import java.beans.ConstructorProperties;
import java.io.Serializable;

public record ChatPayloadRecord(String role, String content) implements Serializable {
}
