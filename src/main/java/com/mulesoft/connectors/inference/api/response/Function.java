package com.mulesoft.connectors.inference.api.response;

import java.io.Serializable;

public record Function(String name,String arguments)implements Serializable{}
