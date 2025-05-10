package com.mulesoft.connectors.internal.models.moderation;

import java.util.Arrays;
import java.util.Set;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

@Deprecated
public class ModerationTypeProvider implements ValueProvider {

  @Override
  public Set<Value> resolve() throws ValueResolvingException {
    return ValueBuilder.getValuesFor(Arrays.stream(ModerationModelType.values()).map(ModerationModelType::name));
  }
    
}
