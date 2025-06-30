package com.mulesoft.connectors.inference.internal.dto.textgeneration.response.vertexai;

import java.util.List;

public record UsageMetadata(int promptTokenCount,int candidatesTokenCount,int totalTokenCount,String trafficType,List<TokenDetails>promptTokensDetails,List<TokenDetails>candidatesTokensDetails,int thoughtsTokenCount){}
