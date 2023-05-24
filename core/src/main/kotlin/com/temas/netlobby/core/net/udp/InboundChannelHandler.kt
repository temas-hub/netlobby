package com.temas.netlobby.core.net.udp

import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.status.InboundHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

/**
 * Netty handler for receiving updates from server. It just passes updates to the updateHandler
 */
class InboundChannelHandler(private val updateHandler: InboundHandler): SimpleChannelInboundHandler<StateMessage>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: StateMessage) {
        updateHandler(msg.state)
    }
}