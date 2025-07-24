package com.mulesoft.connectors.inference.internal.dto.moderation.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record ModerationRestResponse(String id,String model,List<ModerationResult>results,Usage usage){}

