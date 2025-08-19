package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FunctionSchema(
// --- Core Properties ---
String type,String description,@JsonProperty("enum")List<String>enumValues,

// --- Semantic Fields ---
String format,List<Object>examples,

// --- Advanced Structural Fields ---
@JsonProperty("$defs")Map<String,FunctionSchema>defs,List<FunctionSchema>allOf,List<FunctionSchema>anyOf,List<FunctionSchema>oneOf,@JsonProperty("$ref")String ref,

// --- For 'object' type ---
Map<String,FunctionSchema>properties,List<String>required,Boolean additionalProperties,Integer minProperties,Integer maxProperties,

// --- For 'array' type ---
FunctionSchema items,Integer minItems,Integer maxItems,Boolean uniqueItems,

// --- For 'string' type ---
Integer minLength,Integer maxLength,String pattern,

// --- For 'number' or 'integer' type ---
BigDecimal minimum,BigDecimal maximum,Boolean exclusiveMinimum,Boolean exclusiveMaximum,

// --- Generic Validation ---
@JsonProperty("const")Object constValue,@JsonProperty("default")Object defaultValue

)implements Serializable{}
