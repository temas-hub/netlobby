package com.temas.netlobby.core.net

import com.temas.netlobby.core.net.udp.UdpUpstreamHandler
import io.netty.channel.*
import io.netty.handler.ssl.SslContext
/**
 * Channel initializer builds inbound and outbound handlers chain. Also optionally adds ssl handler
 */
class DefaultChannelInitializer(private val sslCtx: SslContext? = null,
                                private val upSteamHandler: UdpUpstreamHandler,
                                private val messageEncoder: MessageEncoder,
                                private val channelHandlers: List<ChannelHandler>? = null
                                ) : ChannelInitializer<Channel>() {
    public override fun initChannel(ch: Channel) {
        val p: ChannelPipeline = ch.pipeline()
        sslCtx?.let { p.addLast(it.newHandler(ch.alloc())) }

        p.addLast("upstreamHandler", upSteamHandler)
        p.addLast("messageEncoder", messageEncoder)

        channelHandlers?.forEach { p.addLast(it) }
    }
}