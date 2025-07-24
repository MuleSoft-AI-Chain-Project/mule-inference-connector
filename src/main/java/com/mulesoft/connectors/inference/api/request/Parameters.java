package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record Parameters(String type,Map<String,Property>properties,List<String>required,boolean additionalProperties)implements Serializable{}
