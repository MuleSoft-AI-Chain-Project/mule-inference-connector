package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)public record Property(String type,String description,@JsonProperty("enum")List<String>enumValues)implements Serializable{}
