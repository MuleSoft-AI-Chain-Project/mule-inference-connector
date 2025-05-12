/**
 * (c) 2003-2024 MuleSoft, Inc. The software in this package is published under the terms of the Commercial Free Software license V.1 a copy of which has been included with this distribution in the LICENSE.md file.
 */
package com.mulesoft.connectors.internal.models.provider;

import java.util.Set;

import com.mulesoft.connectors.internal.models.ModelType;
import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

@Deprecated
public class ModelNameProvider implements ValueProvider {

 /* @Parameter
  @Placement(order = 1)
  private String inferenceType;
*/

  @Override
  public Set<Value> resolve() throws ValueResolvingException {
    return ValueBuilder.getValuesFor(ModelType.fromValue("openAI").getModelNameStream());
  }

}
