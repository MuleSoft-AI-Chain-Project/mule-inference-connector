package com.mulesoft.connectors.internal.dto.vertexai.google;


import javax.mail.Part;
import java.util.List;

public record SystemInstructionDTO(List<Part> parts) {
}