package com.temas.netlobby.core

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class MessageDecoder(private val serializer: MessageSerializer) : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
        if (byteBuf.readableBytes() > 0) {
            out.add(serializer.decode(byteBuf.readBytes(byteBuf.readableBytes()).array()))
        }
    }
}