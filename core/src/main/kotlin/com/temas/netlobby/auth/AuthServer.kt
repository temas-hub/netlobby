package com.temas.netlobby.auth

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate


class AuthServer {
    fun init(sslEnabled: Boolean, port: Int) {
        val sslCtx: SslContext?
        sslCtx = if (sslEnabled) {
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            null
        }

        val bossGroup: EventLoopGroup = NioEventLoopGroup(1)
        val workerGroup: EventLoopGroup = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .handler(LoggingHandler(LogLevel.INFO))
                    .childHandler(AuthServerInitializer(sslCtx))
            val ch: Channel = b.bind(port).sync().channel()
            System.err.println("Open your web browser and navigate to " +
                    (if (sslEnabled) "https" else "http") + "://127.0.0.1:" + port + '/')
            ch.closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    class AuthServerInitializer(private val sslCtx: SslContext?) : ChannelInitializer<SocketChannel>() {
        public override fun initChannel(ch: SocketChannel) {
            val p: ChannelPipeline = ch.pipeline()
            if (sslCtx != null) {
                p.addLast(sslCtx.newHandler(ch.alloc()))
            }
//            p.addLast(HttpRequestDecoder())
//            p.addLast(HttpResponseEncoder())
            p.addLast(HandshakeServerHandler())
        }
    }

    class HandshakeServerHandler : SimpleChannelInboundHandler<Any?>() {

        override fun channelReadComplete(ctx: ChannelHandlerContext) {
            ctx.flush()
        }

        override fun channelRead0(ctx: ChannelHandlerContext, msg: Any?) {
//            ctx.write(response)
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
        }
    }
}