package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai;

public record Candidate(Content content,String finishReason,double avgLogprobs){}
