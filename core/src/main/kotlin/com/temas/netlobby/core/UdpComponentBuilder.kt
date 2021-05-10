package com.temas.netlobby.core

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.server.ServerKoinContext
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.koin.core.Koin
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.core.module.Module
import org.koin.environmentProperties

abstract class UdpComponentBuilder<T, in M: Message> {
    var inboundHandlers: List<(Message) -> Unit>? = null

    fun inboundHandlers(inboundHandlers: List<(Message) -> Unit>) = apply { this.inboundHandlers = inboundHandlers }

    fun buildKoin(vararg modules: Module): Koin {
        val handlers = this.inboundHandlers ?: listOf { msg -> println(msg) }
        val koinApp = koinApplication {
            printLogger()
            environmentProperties()
            modules(serializationModule, channelModule, *modules, module {
                single(named("channelHandlers")) {
                    handlers.map { object: SimpleChannelInboundHandler<M>() {
                        override fun channelRead0(ctx: ChannelHandlerContext, msg: M) {
                            it(msg);
                        }
                    }}.toList()
                }
            })
        }
        ServerKoinContext.koinApp = koinApp
        return koinApp.koin
    }

    abstract fun build(): T
}