package com.mulesoft.connectors.inference.internal.dto.vision.gemini;

import java.util.List;

public record VisionContentRecord(String role,List<Part>parts){}
