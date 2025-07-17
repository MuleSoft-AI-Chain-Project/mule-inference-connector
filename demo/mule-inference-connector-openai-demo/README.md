MuleSoft Inference Connector Demo
====================================
Anypoint Studio demo for MuleSoft Inference Connector using _**OpenAI Inference**_ types.
For using other inference types, proper connection config needs to be changed


Introduction
---------------

This demo application is tests the following operations:
- Chat Answer Prompt
- Chat Completions
- Agent Define Prompt Template
- Tools Native Template
- MCP Tooling
- Read Image 
- Image Generation
- Toxicity Detection by Text

Prerequisites
---------------

* Anypoint Studio 7 with Mule ESB 4.9.4 Runtime and Java 17
* Mulesoft Inference Connector v1.0.0


How to Run Sample
-----------------

1. Import the project folder demo in Studio.
2. Update the Api Key and llmModel(vision and image models) based on the operation to be used in the Connection config
3. Save the configuration & run the application


About the Sample
----------------

You can use postman to trigger curls under the web server http://localhost:8081

## Endpoints

* POST - /prompttemplate (Agent Define Prompt template)
* POST - /chatprompt (Chat answer prompt)
* POST - /chatcompletion (Chat Completions)
* POST - /toolstemplate (Tools Native Template)
* POST - /mcptooling (MCP tooling)
* POST - /readimage (Read Image)
* POST - /toxicitydetection (Moderation)
* POST - /generateimage (Generate Image)
