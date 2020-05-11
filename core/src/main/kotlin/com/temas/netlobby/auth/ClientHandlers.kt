package com.temas.netlobby.auth

import com.temas.netlobby.core.Message
import com.temas.netlobby.core.SimpleMessageHandler
import io.netty.channel.ChannelHandlerContext

open class ClientHandler<in T: Message>(private val messageNotifier: (e: T) -> Unit): SimpleMessageHandler<T>() {
    override fun handle(ctx: ChannelHandlerContext, message: T) {
        message.apply(messageNotifier)
    }
}


