package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.anthropic;

import java.util.Map;

public record Content(String type,String text,Map<String,Object>input,String name,String id){}
