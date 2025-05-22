package com.mulesoft.connectors.inference.internal.connection;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.lifecycle.Disposable;
import org.mule.runtime.api.lifecycle.Initialisable;

public abstract class ModerationConnectionProvider extends BaseConnectionProvider
        implements CachedConnectionProvider<ModerationConnection>, Initialisable, Disposable {

}
