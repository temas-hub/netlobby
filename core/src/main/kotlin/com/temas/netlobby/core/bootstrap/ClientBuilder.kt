package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.core.*
import com.temas.netlobby.core.net.udp.UDPClient
import com.temas.netlobby.core.status.*
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.environmentProperties

class ClientBuilder {

    lateinit var inboundHandler: InboundHandler

    class Client(private val koin: Koin): NetLobbyClient, KoinComponent {
        override fun getKoin(): Koin = this.koin
        val networkClient: UDPClient = get()

        override suspend fun connect(): Connection {
            return Connection(networkClient.connect().channel())
        }
    }

    fun withInboundHandler(inboundHandler: (state: ServerState) -> Unit) = apply { this.inboundHandler = inboundHandler }

    private fun handler(clientUpdateHandler: InboundHandler) =
            object : SimpleChannelInboundHandler<StateMessage>() {
                override fun channelRead0(ctx: ChannelHandlerContext, msg: StateMessage) {
                    clientUpdateHandler(msg.state);
                }
            }

    fun build(): NetLobbyClient {
        val koinApp = koinApplication {
            printLogger(Level.ERROR)
            environmentProperties()
            modules(
                serializationModule,
                channelModule,
                clientModule,
                module {
                        factory(named("channelHandlers")) {
                            listOf(handler(inboundHandler))
                        }
                 }
            )
        }
        ClientKoinContext.koinApp = koinApp
        return Client(koinApp.koin)
    }
}

fun main() {
    val client = ClientBuilder()
        .withInboundHandler { println(it) }
        .build()

    class SampleActionType : ActionType()
    client.connect()
    client.sendActions(listOf(Action(SampleActionType())))
}