package com.mulesoft.connectors.inference.internal.extension;

import static org.mule.sdk.api.meta.JavaVersion.JAVA_17;

import org.mule.runtime.api.meta.Category;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.SubTypeMapping;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;
import org.mule.sdk.api.annotation.JavaVersionSupport;

import com.mulesoft.connectors.inference.internal.config.ImageGenerationConfig;
import com.mulesoft.connectors.inference.internal.config.ModerationConfig;
import com.mulesoft.connectors.inference.internal.config.TextGenerationConfig;
import com.mulesoft.connectors.inference.internal.config.VisionConfig;
import com.mulesoft.connectors.inference.internal.config.proxy.DefaultNtlmProxyConfig;
import com.mulesoft.connectors.inference.internal.config.proxy.DefaultProxyConfig;
import com.mulesoft.connectors.inference.internal.config.proxy.HttpProxyConfig;
import com.mulesoft.connectors.inference.internal.error.InferenceErrorType;

/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations and
 * sources are going to be declared.
 */
@Xml(prefix = "mac-inference")
@Extension(name = "MuleSoft Inference", category = Category.SELECT)
@ErrorTypes(InferenceErrorType.class)
@JavaVersionSupport({JAVA_17})
@SubTypeMapping(baseType = HttpProxyConfig.class, subTypes = {DefaultProxyConfig.class, DefaultNtlmProxyConfig.class})
@Configurations({
    TextGenerationConfig.class,
    VisionConfig.class,
    ImageGenerationConfig.class,
    ModerationConfig.class
})
public class InferenceConnector {

}
