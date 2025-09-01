package com.mulesoft.connectors.inference.api.request;

import org.mule.runtime.extension.api.annotation.Alias;

/**
 * Represents an HTTP Header
 */
@Alias("defaultHeader")
public class RequestHeader extends KeyValue {

  public RequestHeader() {
    // default constructor
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
