# MCP Demo

This demonstrates integrating custom behaviour into a locally deployed LLM 
using the MCP protocol implemented by an api developed in Kotlin.

The MCP api provides a service that supplies a 'Frank score' for anything
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

Build the Kotlin api:

```bash
./gradlew shadowJar
```

Start the LLM backend and the MCP:
```bash
docker-compose up --detach
```

Start the mcphost console to chat with the llm:
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
