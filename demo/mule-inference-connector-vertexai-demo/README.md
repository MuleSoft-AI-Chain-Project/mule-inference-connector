# MuleSoft Inference Connector Test App

> **Note:** This demo application is still a work in progress and some operations may be missing or incomplete.

## Introduction

This demo application is tests te following operations:
- Chat-Answer-Prompt
- Agent-Prompt-Template
- Tools-Function
- Read Image (using Base64 string)
- Read Image (via fileURI)

## Models & Operations supported by each provider
Google Gemini models - Chat-Answer Prompt, Prompt-Template,	ReadImage (Base64), ReadImage (fileURI), Tools-func 
Anthropic claude models - Chat-Answer Prompt, Prompt-Template,	ReadImage (Base64)
Meta Llama models - Chat-Answer Prompt, Prompt-Template,	ReadImage (Base64), ReadImage (fileURI)

## Prerequisites

- MuleSoft Inference Connector

## Configure your application
### For Vertex AI Express
Configure /src/main/resources/llm.properties with the following properties:
llm=VERTEX_AI_EXPRESS
api.key={API KEY for Vertex AI Express}
model.name={model name, ex: gpt-4o}

### For Vertex AI
Configure /src/main/resources/llm.properties with the following properties:
llm=VERTEX_AI
model.name={model name, ex: gpt-4o}
vertexAIProjectId={Vertex AI Project Id}
vertexAILocationId={Vertex AI Location Id, ex: us-central1}

In addtion, copy the service account key file to /src/main/resources/gcp folder and configure the '[Vertex AI] Service Account Key' property in Additional Properties of the Connector Config accordingly, ex below:
mule.home ++ '/apps/' ++ app.name ++ '/gcp/service-account.json'

## How to test the different operations?
Use the attached Postman Collection - Inference-Vertex-AI.postman_collection.json