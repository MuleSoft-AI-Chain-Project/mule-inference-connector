package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)public record Function(String name,String description,Parameters parameters)implements Serializable{}
