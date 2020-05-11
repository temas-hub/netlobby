package com.temas.netlobby.core

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder


class MessageEncoder(private val serializer: MessageSerializer) : MessageToByteEncoder<Message>() {
    override fun encode(ctx: ChannelHandlerContext?, msg: Message, out: ByteBuf) {
        out.writeBytes(serializer.encode(msg))
    }
}