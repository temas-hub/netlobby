package com.temas.netlobby.auth

import com.temas.netlobby.core.AuthRequest
import com.temas.netlobby.core.MessageHandler
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


class AuthService : MessageHandler<AuthRequest>{




    class HandshakeServerHandler : SimpleChannelInboundHandler<Any?>() {

        override fun channelReadComplete(ctx: ChannelHandlerContext) {
            ctx.flush()
        }

        override fun channelRead0(ctx: ChannelHandlerContext, msg: Any?) {
//            ctx.write(response)
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE)
        }
    }

    override fun handle(message: AuthRequest) {
    }
}