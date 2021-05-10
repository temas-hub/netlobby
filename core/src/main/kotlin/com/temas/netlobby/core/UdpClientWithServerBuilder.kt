package com.temas.netlobby.core

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientWithServerModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.server.ServerKoinContext
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.environmentProperties

class UdpClientWithServerBuilder {

    var clientInboundHandlers: Array<(Message) -> Unit> = arrayOf({ msg -> println(msg) })
    var serverInboundHandlers: Array<(Message) -> Unit> = arrayOf({ msg -> println(msg) })


    private fun handlers(handlers: Array<(Message) -> Unit>) =
        listOf(*handlers).map {
            object : SimpleChannelInboundHandler<Message>() {
                override fun channelRead0(ctx: ChannelHandlerContext, msg: Message) {
                    it(msg);
                }
            }
        }
    fun build(): Koin {
        val koinApp = koinApplication {
            printLogger()
            environmentProperties()
            modules(serializationModule, channelModule, clientWithServerModule,
                module {
                    scope(named("client")) {
                        factory(named("channelHandlers")) {
                            handlers(clientInboundHandlers)
                        }
                    }
                    scope(named("server")) {
                        factory(named("channelHandlers")) {
                            handlers(serverInboundHandlers)
                        }
                    }
                }
            )}
            ServerKoinContext.koinApp = koinApp
            return koinApp.koin
    }
}

fun main() {

//    var latestServerTimestamp: Long? = null
//    val builder = UdpClientWithServerBuilder()
//    builder.clientInboundHandlers = arrayOf({ m -> latestServerTimestamp = (m as StatusMessage).status.serverTimestamp})
//    val koin = builder.build()
//    val serverScope = koin.createScope<UDPServer>()
//    val updater = serverScope.get<UpdateSender>()
//    updater.sendUpdates()
//    serverScope.get<UDPServer>().init()
//    val clientScope = koin.createScope<UDPClient>()
//    val client = clientScope.get<UDPClient>()
//    val channel = client.connect().channel()
//    Timer("Update sender", false).schedule(0,2000) {
//        val statusMessage = TransferableMessage(null, StatusMessage(Status(latestServerTimestamp)))
//        channel.writeAndFlush(statusMessage)
//    }
}