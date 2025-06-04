package com.mulesoft.connectors.inference.internal.dto.vision.vertexai;

import com.mulesoft.connectors.inference.internal.dto.vision.TextContent;

public record Part(InlineData inlineData,FileData fileData,TextContent textContent){}
