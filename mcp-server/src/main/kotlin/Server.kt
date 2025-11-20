import io.ktor.server.engine.*
import io.modelcontextprotocol.kotlin.sdk.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.mcp
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonObject

object McpServer {
    fun start() {
        embeddedServer(io.ktor.server.cio.CIO, host = "127.0.0.1", port = 8080) {
            mcp {
                configureServer()
            }
        }.start(wait = true)
    }

    fun configureServer(): Server {
        val server = Server(
            Implementation(
                name = "Mcp server",
                version = "0.1.0",
            ),
            ServerOptions(
                capabilities = ServerCapabilities(
                    tools = ServerCapabilities.Tools(listChanged = true),
                ),
            ),
        )

        server.addTool(
            name = "frank-score-tool",
            description = "Provides a 'Frank Score' for anything.",
            inputSchema = Tool.Input(
                properties = buildJsonObject {
                    putJsonObject("name") {
                        put("type", JsonPrimitive("string"))
                        put("description", JsonPrimitive("The name of the thing to score."))
                    }
                },
                required = listOf("name")
            ),
        ) { input: CallToolRequest ->
            val nameOfThingToScore = input.arguments["name"].toString()
            CallToolResult(
                content = listOf(TextContent("${nameOfThingToScore.hashCode()}")),
            )
        }

        return server
    }

}