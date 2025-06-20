package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)@JsonIgnoreProperties(ignoreUnknown=true)public record Property(String type,String description,@JsonProperty("enum")List<String>enumValues,@JsonProperty("properties")Map<String,Property>properties,Property items // Dedicated
                                                                                                                                                                                                                                                              // field
                                                                                                                                                                                                                                                              // for
                                                                                                                                                                                                                                                              // array
                                                                                                                                                                                                                                                              // items
)implements Serializable{}
