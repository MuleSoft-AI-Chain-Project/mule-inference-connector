# <img src="icon/icon.svg" width="6%" alt="banner"> MuleSoft Inference Connector
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mulesoft-ai-chain-project/mule4-inference-connector)](https://central.sonatype.com/artifact/io.github.mulesoft-ai-chain-project/mule4-inference-connector/overview)

## <img src="https://raw.githubusercontent.com/MuleSoft-AI-Chain-Project/.github/main/profile/assets/mulechain-project-logo.png" width="6%" alt="banner">   [MuleSoft AI Chain (MAC) Project](https://mac-project.ai/docs/)

### <img src="icon/icon.svg" width="6%" alt="banner"> MuleSoft Inference Connector

MuleSoft Inference Connector provides access to Inference Offering for Large Language Models i.e. Groq, Hugging Face, Github Models, etc.

The MuleSoft Inference Connector supports the following Inference Offerings:

## Supported Inference Providers
- [AI21Labs](https://studio.ai21.com/)
- [Anthropic](https://www.anthropic.com/)
- [Azure AI Foundry](https://learn.microsoft.com/en-us/azure/ai-foundry/)
- [Azure OpenAI](https://learn.microsoft.com/en-us/azure/ai-services/openai/)
- [Cerebras](https://cerebras.ai/inference)
- [Cohere](https://cohere.com/)
- [Databricks](https://docs.databricks.com/aws/en/machine-learning/model-serving/score-foundation-models?language=REST%C2%A0API)
- [DeepInfra](https://deepinfra.com/)
- [DeepSeek](https://api-docs.deepseek.com/)
- [Docker Models](https://docs.docker.com/desktop/features/model-runner/)
- [Fireworks](https://fireworks.ai/)
- [GitHub Models](https://docs.github.com/en/github-models)
- [Google Vertex AI](https://cloud.google.com/vertex-ai?hl=en)
- [Groq AI](https://console.groq.com/)
- [Hugging Face](https://huggingface.co/)
- [IBM Watson](https://www.ibm.com/products/watsonx-ai)
- [LLM API](https://llmapi.com/)
- [Mistral](https://www.mistral.ai/)
- [NVIDIA](https://www.nvidia.com/en-sg/ai)
- [Ollama](https://ollama.com/)
- [OpenAI](https://openai.com/)
- [OpenAI Compatible Endpoints](https://platform.openai.com/docs/api-reference/introduction)
- [OpenRouter](https://openrouter.ai/)
- [Perplexity](https://www.perplexity.ai/)
- [Portkey](https://portkey.ai/)
- [Together.ai](https://www.together.ai/)
- [XAI](https://x.ai/)
- [Xinference](https://inference.readthedocs.io/)
- [ZHIPU AI](https://open.bigmodel.cn/dev/api/normal-model/glm-4)

## Supported Moderation Providers
- [Mistral AI](https://docs.mistral.ai/capabilities/guardrailing/)
- [OpenAI](https://openai.com/)

## Supported Vision Model Providers
- [Anthropic](https://www.anthropic.com/)
- [Azure AI Foundry](https://learn.microsoft.com/en-us/azure/ai-foundry/)
- [GitHub Models](https://docs.github.com/en/github-models)
- [Google Vertex AI](https://cloud.google.com/vertex-ai?hl=en)
- [Groq AI](https://console.groq.com/)
- [Hugging Face](https://huggingface.co/)
- [Mistral](https://docs.mistral.ai/capabilities/vision/)
- [OpenAI](https://platform.openai.com/docs/guides/images?api-mode=chat)
- [OpenAI Compatible Endpoints](https://platform.openai.com/docs/api-reference/introduction)
- [OpenRouter](https://openrouter.ai/)
- [Portkey](https://portkey.ai/)
- [XAI](https://x.ai/)

## Supported Image Models Providers
- [Hugging Face](https://huggingface.co/)
- [OpenAI](https://platform.openai.com/docs/guides/images?api-mode=chat)
- [Stability_AI](https://platform.stability.ai/docs/api-reference#tag/Generate/paths/~1v2beta~1stable-image~1generate~1sd3/post)
- [XAI](https://docs.x.ai/docs/guides/image-generations#image-generations)

## HTTPS Security
The MuleSoft Inference Connector support [TLS for Mule Apps](https://docs.mulesoft.com/mule-runtime/latest/tls-configuration)

## Requirements
- The supported version for Java SDK is Java 17.
- Compilation of the connector has to be done with Java 17.
- Mule Runtimes with Java 17 are supported.

## Installation (using maven central dependency)

```xml
<dependency>
   <groupId>io.github.mulesoft-ai-chain-project</groupId>
   <artifactId>mule4-inference-connector</artifactId>
   <version>{version}</version>
   <classifier>mule-plugin</classifier>
</dependency>
```

## Installation (building locally)

To use this connector, first [build and install](https://mac-project.ai/docs/mac-inference/getting-started) the connector into your local maven repository.
Then add the following dependency to your application's `pom.xml`:

```xml
<dependency>
    <groupId>com.mulesoft.connectors</groupId>
    <artifactId>mule4-inference-connector</artifactId>
    <version>{version}</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

## Formatting

The connector uses **formatter-maven-plugin** for formatting. 
To format files use following maven command :

```
mvn formatter:format
```

## Import Sorting

The connector uses **impsort-maven-plugin** for sorting imports.
To sort imports use following maven command :

```
mvn impsort:sort
```

## Installation into private Anypoint Exchange

You can also make this connector available as an asset in your Anyooint Exchange.

This process will require you to build the connector as above, but additionally you will need
to make some changes to the `pom.xml`.  For this reason, we recommend you fork the repository.

Then, follow the MuleSoft [documentation](https://docs.mulesoft.com/exchange/to-publish-assets-maven) to modify and publish the asset.

## Documentation 
- Check out the complete documentation in [mac-project.ai](https://mac-project.ai/docs/mulechain-vectors)
- Learn from the [Getting Started YouTube Playlist](https://www.youtube.com/playlist?list=PLnuJGpEBF6ZAV1JfID1SRKN6OmGORvgv6)

----

## Stay tuned!

- 🌐 **Website**: [mac-project.ai](https://mac-project.ai)
- 📺 **YouTube**: [@MuleSoft-MAC-Project](https://www.youtube.com/@MuleSoft-MAC-Project)
- 💼 **LinkedIn**: [MAC Project Group](https://lnkd.in/gW3eZrbF)

