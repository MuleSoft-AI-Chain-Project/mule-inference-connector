package com.mulesoft.connectors.inference.internal.dto.textgeneration.gemini;

// import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record GeminiProperty(String type,String description,@JsonIgnore List<String>enumValues){}
