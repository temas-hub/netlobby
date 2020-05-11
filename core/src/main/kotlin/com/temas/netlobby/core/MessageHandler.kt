package com.temas.netlobby.core

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

interface MessageHandler<in T: Message> : ChannelHandler {
    fun handle(ctx: ChannelHandlerContext, message: T)
}

interface MessageHandlerProvider {
    fun getHandlers(): List<MessageHandler<Message>>
}

@Suppress("UNCHECKED_CAST")
abstract class SimpleMessageHandler<in T: Message> : SimpleChannelInboundHandler<Message>(true), MessageHandler<T> {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: Message) {
        handle(ctx, msg as T)
    }
}