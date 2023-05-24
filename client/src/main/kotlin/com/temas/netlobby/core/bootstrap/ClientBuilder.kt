package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.core.api.NetLobbyClient
import com.temas.netlobby.core.net.udp.InboundChannelHandler
import com.temas.netlobby.core.status.*
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.environmentProperties

/**
 * Koin application builder for client
 */
class ClientBuilder {

    val channelHandlerModule = module {
        factory(named("channelHandlers")) {
            listOf(InboundChannelHandler(inboundHandler))
        }
    }

    private lateinit var inboundHandler: InboundHandler

    fun withInboundHandler(inboundHandler: (state: ServerState) -> Unit) = apply { this.inboundHandler = inboundHandler }


    /**
     * Builds client
     */
    fun build(): NetLobbyClient {
        val koinApp = koinApplication {
            printLogger(Level.ERROR)
            environmentProperties()
            modules(
                serializationModule,
                channelModule,
                clientModule,
                channelHandlerModule
            )
        }
        ClientKoinContext.koinApp = koinApp
        return Client(koinApp.koin)
    }
}

fun main() {

    val client = ClientBuilder()
        .withInboundHandler {
            println(it)
        }
        .build()

    val connection = client.connect()
    connection.sendActions(listOf(Action(DummyActionType())))
}