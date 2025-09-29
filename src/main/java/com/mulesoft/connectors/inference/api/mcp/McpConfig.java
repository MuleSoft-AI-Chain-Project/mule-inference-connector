package com.mulesoft.connectors.inference.api.mcp;

import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.reference.ConfigReference;

import java.util.Objects;

public class McpConfig {

  /**
   * Reference to the MCP client configuration defined as part of configuration from the MCP connector.
   *
   * <p>
   * This value must match the {@code name} of a {@code mcp:client-config} declared in the global configuration. It is used to
   * discover and execute tools against the targeted MCP server.
   * </p>
   */
  @Parameter
  @ConfigReference(namespace = "MCP", name = "CLIENT")
  private String mcpClientConfigRef;

  public String getMcpClientConfigRef() {
    return mcpClientConfigRef;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    McpConfig mcpConfig = (McpConfig) o;
    return Objects.equals(mcpClientConfigRef, mcpConfig.mcpClientConfigRef);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(mcpClientConfigRef);
  }
}
