package com.mulesoft.connectors.inference.api.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public record ModerationResponse( boolean flagged,
                                  List<Map<String, Double>> categories) implements Serializable {}