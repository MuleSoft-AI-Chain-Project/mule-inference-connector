# <img src="icon/icon.svg" width="6%" alt="banner"> MuleSoft Inference Connector
[![Maven Central](https://img.shields.io/maven-central/v/io.github.mulesoft-ai-chain-project/mule4-inference-connector)](https://central.sonatype.com/artifact/io.github.mulesoft-ai-chain-project/mule4-inference-connector/overview)

## <img src="https://raw.githubusercontent.com/MuleSoft-AI-Chain-Project/.github/main/profile/assets/mulechain-project-logo.png" width="6%" alt="banner">   [MuleSoft AI Chain (MAC) Project](https://mac-project.ai/docs/)

### <img src="icon/icon.svg" width="6%" alt="banner"> MuleSoft Inference Connector

MuleSoft Inference Connector provides access to Inference Offering for Large Language Models i.e. Groq, Hugging Face, Github Models, etc.

## Supported Inference Providers
The MuleSoft Inference Connector supports the following Inference Offerings:

- [GitHub Models](https://docs.github.com/en/github-models)
- [Hugging Face](https://huggingface.co/)
- [Ollama](https://ollama.com/)
- [Groq AI](https://console.groq.com/)
- [Portkey](https://portkey.ai/)
- [OpenAI](https://openai.com/)
- [Anthropic](https://www.anthropic.com/)
- [XAI](https://x.ai/)
- [Perplexity](https://www.perplexity.ai/)
- [Mistral](https://www.mistral.ai/)
- [OpenRouter](https://openrouter.ai/)
- [Cerebras](https://cerebras.ai/inference)
- [NVIDIA](https://www.nvidia.com/en-sg/ai)
- [Together.ai](https://www.together.ai/)
- [Fireworks](https://fireworks.ai/)
- [DeepInfra](https://deepinfra.com/)
- [AI21Labs](https://studio.ai21.com/)
- [Cohere](https://cohere.com/)
- [Xinference](https://inference.readthedocs.io/)
- [Azure OpenAI](https://learn.microsoft.com/en-us/azure/ai-services/openai/)
- [Google Vertex AI](https://cloud.google.com/vertex-ai?hl=en)

## Supported Moderation Providers
- [OpenAI](https://openai.com/)
- [Mistral AI](https://docs.mistral.ai/capabilities/guardrailing/)

## Supported Vision Model Providers
- [OpenAI](https://platform.openai.com/docs/guides/images?api-mode=chat)
- [Anthropic](https://www.anthropic.com/)
- [XAI](https://x.ai/)
- [Mistral](https://docs.mistral.ai/capabilities/vision/)
- [OpenRouter](https://openrouter.ai/)
- [Groq AI](https://console.groq.com/)
- [Portkey](https://portkey.ai/)

### Requirements

- The maximum supported version for Java SDK is JDK 17. You can use JDK 17 only for running your application.
- Compilation with Java SDK must be done with JDK 8.

### Installation (using maven central dependency)

```xml
<dependency>
   <groupId>io.github.mulesoft-ai-chain-project</groupId>
   <artifactId>mule4-inference-connector</artifactId>
   <version>{version}</version>
   <classifier>mule-plugin</classifier>
</dependency>
```

### Installation (building locally)

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

### Installation into private Anypoint Exchange

You can also make this connector available as an asset in your Anyooint Exchange.

This process will require you to build the connector as above, but additionally you will need
to make some changes to the `pom.xml`.  For this reason, we recommend you fork the repository.

Then, follow the MuleSoft [documentation](https://docs.mulesoft.com/exchange/to-publish-assets-maven) to modify and publish the asset.

### Documentation 
- Check out the complete documentation in [mac-project.ai](https://mac-project.ai/docs/mulechain-vectors)
- Learn from the [Getting Started YouTube Playlist](https://www.youtube.com/playlist?list=PLnuJGpEBF6ZAV1JfID1SRKN6OmGORvgv6)

----

### Stay tuned!

- 🌐 **Website**: [mac-project.ai](https://mac-project.ai)
- 📺 **YouTube**: [@MuleSoft-MAC-Project](https://www.youtube.com/@MuleSoft-MAC-Project)
- 💼 **LinkedIn**: [MAC Project Group](https://lnkd.in/gW3eZrbF)

