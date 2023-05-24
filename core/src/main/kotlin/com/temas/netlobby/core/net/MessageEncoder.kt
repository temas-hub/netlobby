package com.temas.netlobby.core.net

import com.temas.netlobby.core.MessageSerializer
import com.temas.netlobby.core.TransferableMessage
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageEncoder


/**
 * Encodes object into bytes before sending
 */
class MessageEncoder(private val serializer: MessageSerializer): MessageToMessageEncoder<TransferableMessage>() {

    override fun encode(ctx: ChannelHandlerContext, msg: TransferableMessage, out: MutableList<Any>) {
        val buffer = ctx.alloc().ioBuffer()
        buffer.writeBytes(serializer.encode(msg.data))
        if (msg.address == null) {
            out.add(buffer)
        } else {
            out.add(DatagramPacket(buffer, msg.address))
        }
    }
}