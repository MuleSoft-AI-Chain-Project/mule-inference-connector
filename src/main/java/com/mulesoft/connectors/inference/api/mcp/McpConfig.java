package com.mulesoft.connectors.inference.api.mcp;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.reference.ConfigReference;

public class McpConfig {

  @Parameter
  @ConfigReference(namespace = "MCP", name = "CLIENT")
  private String mcpClientConfigRef;

  public String getMcpClientConfigRef() {
    return mcpClientConfigRef;
  }
}
