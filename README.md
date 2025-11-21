# MCP Demo

This demonstrates integrating custom behaviour into a locally deployed LLM 
using the MCP protocol implemented by an api developed in Kotlin.

The MCP api provides a service that supplies a notional 'Frank score' for anything
with a name.

This adds support to the LLM to answer questions such as:

```text
Whats Bobs Frank score?
```

## Instructions

The following pre-requisites are required:
- Java JDK
- Docker
- Docker Compose

Build the Kotlin MCP server:

```bash
./gradlew shadowJar
```

Start the LLM backend and the MCP server:
```bash
docker-compose up --detach
```

Start the **mcphost** console to chat with the llm. Note this will trigger the
downloading of the LLM model files which could take some time:
```bash
docker-compose run --rm --build mcphost
```

Enter the prompt:
```text
Whats Bobs frank score?
```

You should see the response:

```text
Bobs' Frank Score is: 1037748098 
```

## What's happening.

The LLM is the llama3.2:3b model published by Meta, its being run on Ollama an 
open source tool that allows lots of different models to be run easily.

Ollama is run by spinning up the vanilla ollama docker container. 

Models can be downloaded and run by making api calls to the ollama service or 
using the cli. In this case the running of llama model is triggered by the
'mcphost' client, see below.

Model Context Protocol (MCP) is an api standard designed to make it easy to 
integrate apis with LLMs.

The process loosely works as follows:
- A MCP enabled LLM Client (a host in MCP speak) is configured with details of 
  the MCP server
- The LLM Client interrogates the MCP server which provides text descriptions of the
  endpoints it supports and the parameters they take.
- The LLM Client initiates a session with the LLM and supplies context about the available MCP endpoints.
- When the user enters a prompt into the LLM Client. 
- The LLM Client and LLM negotiate what MCP calls need to be made. 
- The LLM client makes the MCP requests and supplies the responses to the LLM. 
- The responses are included in the LLMs context as it processes the prompt and returns its response.

In this case the LLM Client (or MCP Host) application is [mcphost](https://github.com/mark3labs/mcphost)
which is a LLM console application written in golang that supports both MCP server
integration and ollama hosted LLMs.

The MCP server is implemented using the [MCP Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
which is built on the Ktor framework. The server is configured to use 
Server Side Events (SSE) as the MCP transport. 

mcphost client and MCP server are run using docker compose to simplify demo setup.

## Notes

### Running native version of Ollama

Using docker to run Ollama simplifies the Demo but is limiting in terms of optimising the
running of the LLM for the local environment. Particularly on apple silicone for which the 
use of the GPUs are not supported in docker at all. 

Installing the native version of Ollama will improve the speed at which the LLM provides 
its responses. 

You can install the native version of Ollama as follows:
- Ensure the dockerised version of Ollama is shutdown:
  ```bash
  docker-compose down
  ```
- Download, install and run Ollama. For details [see](https://ollama.com/download).
- You will need to start up the MCP Server separately:
  ```bash
  ./gradlew mcp-server:run
  ```
  or
  ```bash
  ./gradlew mcp-server:shadowJar
  docker-compose run --rm --service-ports mcp-server
  ```
### Running other LLMs

You can run other LLMs with some restrictions:
- Ollama needs to support the LLM. The LLM needs to support use of 'tools'. For a list of these LLMs 
  see [here](https://ollama.com/search?c=tools)
- There may be other restrictions on LLMs supported by the **mcphost** client, for details see 
  wha[here](https://github.com/mark3labs/mcphost?tab=readme-ov-file#available-models)

You can run the demo with a different model by editing 'model' field in the **mcphost** config file:
[mcphost/.mcphost.yml](mcphost/.mcphost.yml)

## Further reading
- [Model Context Protocol](https://modelcontextprotocol.io)
- [mcphost](https://github.com/mark3labs/mcphost)
- [MCP Kotlin SDK](https://github.com/modelcontextprotocol/kotlin-sdk)
- [Ollama](https://ollama.com/)
