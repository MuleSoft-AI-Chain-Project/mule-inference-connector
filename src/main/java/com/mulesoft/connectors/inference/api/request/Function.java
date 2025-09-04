package com.mulesoft.connectors.inference.api.request;

import java.io.Serializable;

public record Function(String name,String description,FunctionSchema parameters)implements Serializable{}
