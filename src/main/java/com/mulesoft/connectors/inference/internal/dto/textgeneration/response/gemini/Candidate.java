package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.gemini;

import com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini.ContentRecord;

public record Candidate(ContentRecord content,String finishReason,double avgLogprobs){}
