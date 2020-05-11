package com.temas.netlobby.auth

import com.temas.netlobby.core.*
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
import java.util.*


class AuthService {


    object HandshakeServerHandler : SimpleMessageHandler<Ping>() {
        override fun handle(ctx: ChannelHandlerContext, message: Ping) {
            ctx.writeAndFlush(Pong)
        }
    }

    object LoginHandler : SimpleMessageHandler<AuthRequest>() {
        override fun handle(ctx: ChannelHandlerContext, message: AuthRequest) {
            ctx.writeAndFlush(SuccessAuthResponse(message.login, UUID.randomUUID()))
        }
    }
}