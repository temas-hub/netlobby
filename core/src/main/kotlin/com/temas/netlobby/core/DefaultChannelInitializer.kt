package com.temas.netlobby.core

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelPipeline
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.ssl.SslContext

class DefaultChannelInitializer(private val sslCtx: SslContext?,
                                private val messageDecoder: MessageDecoder,
                                private val messageEncoder: MessageEncoder,
                                private val channelHandlers: List<ChannelHandler>,
                                private val maxMessageLength: Int) : ChannelInitializer<SocketChannel>() {
    public override fun initChannel(ch: SocketChannel) {
        val p: ChannelPipeline = ch.pipeline()
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()))
        }
        p.addLast("lengthDecoder",
                LengthFieldBasedFrameDecoder(maxMessageLength, 0, 2, 0, 2))
        p.addLast("messageDecoder",  messageDecoder)
        p.addLast("lengthFieldPrepender", LengthFieldPrepender(2))
        p.addLast("messageEncoder", messageEncoder)
        channelHandlers.forEach { h ->
            p.addLast(h)
        }
    }
}