package com.temas.netlobby.core.net

import com.temas.netlobby.core.MessageSerializer
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

/**
 * Decodes bytes into object after receiving
 */
class MessageDecoder(private val serializer: MessageSerializer) : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
        if (byteBuf.readableBytes() > 0) {
            out.add(serializer.decode(byteBuf.readBytes(byteBuf.readableBytes()).array()))
        }
    }
}