package com.mulesoft.connectors.inference.internal.dto.vision;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImageSource(String type,@JsonProperty("media_type")String mediaType,String data,String url){}
