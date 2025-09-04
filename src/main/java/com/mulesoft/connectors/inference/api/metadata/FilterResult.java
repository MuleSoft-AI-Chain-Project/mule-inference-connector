package com.mulesoft.connectors.inference.api.metadata;

import java.io.Serializable;

public record FilterResult(Boolean filtered,Boolean detected,String severity)implements Serializable{}
