package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.vertexai.google.ContentRecord;

public record Candidate(ContentRecord content,String finishReason,double avgLogprobs){}
