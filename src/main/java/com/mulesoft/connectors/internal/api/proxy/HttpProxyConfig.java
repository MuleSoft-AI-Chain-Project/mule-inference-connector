/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package com.mulesoft.connectors.internal.api.proxy;

import org.mule.runtime.http.api.client.proxy.ProxyConfig;

/**
 * Marker interface for exposing the proxy configuration as an imported type.
 */
public interface HttpProxyConfig extends ProxyConfig {

  interface HttpNtlmProxyConfig extends HttpProxyConfig, NtlmProxyConfig {
  }
}
