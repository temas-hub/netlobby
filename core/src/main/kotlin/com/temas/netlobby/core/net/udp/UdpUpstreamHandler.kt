package com.temas.netlobby.core.net.udp

import com.temas.netlobby.core.model.ActionMessage
import com.temas.netlobby.core.MessageSerializer
import com.temas.netlobby.server.communication.SessionRegistry
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.DatagramPacket
import java.lang.Exception

import java.net.InetSocketAddress

/**
 * Inbound handler on server side for receiving messages from clients. It registers communication channels.
 * Once it receives a message from a new client it registers communication session for this client.
 * Then each message is decoded into ActionMessage and applied to the session.
 */
class UdpUpstreamHandler(private val sessionRegistry: SessionRegistry<InetSocketAddress, DatagramChannel>? = null,
                         private val serializer: MessageSerializer): SimpleChannelInboundHandler<DatagramPacket>() {

    override fun channelRead0(ctx: ChannelHandlerContext, packet: DatagramPacket) {
        val session = sessionRegistry?.addSession(packet.sender(), ctx.channel() as DatagramChannel)
        try {
            val byteBuf = packet.content()
            if (byteBuf.readableBytes() > 0) {
                val bytes = ByteArray(byteBuf.readableBytes())
                byteBuf.readBytes(bytes)
                val message = serializer.decode(bytes)
                session?.applyActions((message as ActionMessage).actions)
                ctx.fireChannelRead(message)
            }

        } catch (e: Exception) {
            println(e)
            ctx.fireExceptionCaught(e)
        }

    }
}