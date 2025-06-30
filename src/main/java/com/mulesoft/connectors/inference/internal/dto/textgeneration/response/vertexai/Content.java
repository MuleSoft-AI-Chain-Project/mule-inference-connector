package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai;

import java.util.List;

public record Content(String role,List<Part>parts){}
