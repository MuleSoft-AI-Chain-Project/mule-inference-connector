package com.mulesoft.connectors.inference.api.request;

import static org.mule.runtime.api.meta.ExpressionSupport.NOT_SUPPORTED;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import java.util.Objects;

/**
 * Abstract class to represent a key-value such as headerName-headerValue. Can be later used with query params as well
 */
public abstract class KeyValue {

  @Parameter
  @Expression(NOT_SUPPORTED)
  private String key;

  @Parameter
  @Expression(ExpressionSupport.SUPPORTED)
  private String value;

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    KeyValue keyValue = (KeyValue) o;
    return Objects.equals(key, keyValue.key) && Objects.equals(value, keyValue.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, value);
  }
}

