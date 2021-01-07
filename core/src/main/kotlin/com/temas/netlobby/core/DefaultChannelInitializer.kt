package com.temas.netlobby.core

import com.temas.netlobby.UdpUpstreamHandler
import io.netty.channel.*
import io.netty.handler.ssl.SslContext

class DefaultChannelInitializer(private val sslCtx: SslContext? = null,
                                private val upSteamHandler: UdpUpstreamHandler,
                                private val messageEncoder: MessageEncoder,
                                private val channelHandlers: List<ChannelHandler>? = null
                                ) : ChannelInitializer<Channel>() {
    public override fun initChannel(ch: Channel) {
        val p: ChannelPipeline = ch.pipeline()
        sslCtx?.let { p.addLast(it.newHandler(ch.alloc())) }

        p.addLast("upstreamHandler", upSteamHandler);
        p.addLast("messageEncoder", messageEncoder);

        channelHandlers?.forEach { p.addLast(it) }
    }
}