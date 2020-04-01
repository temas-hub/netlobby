package com.temas.netlobby.core

import com.temas.netlobby.auth.AuthService
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.LengthFieldBasedFrameDecoder
import io.netty.handler.codec.LengthFieldPrepender
import io.netty.handler.codec.MessageToByteEncoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import org.omg.CORBA.Object

class TCPServer {


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
                    .childHandler(TCPServerInitializer(sslCtx))
            val ch: Channel = b.bind(port).sync().channel()
            System.err.println("Open your web browser and navigate to " +
                    (if (sslEnabled) "https" else "http") + "://127.0.0.1:" + port + '/')
            ch.closeFuture().sync()
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    class TCPServerInitializer(private val sslCtx: SslContext?,
                               private val messageDecoder: ByteToMessageDecoder,
                               private val messageEncoder: MessageToByteEncoder<Message>,
                               private val messageHandlersProvider: MessageHandlerProvider,
                               private val maxMessageLength: Int) : ChannelInitializer<SocketChannel>() {
        public override fun initChannel(ch: SocketChannel) {
            val p: ChannelPipeline = ch.pipeline()
            if (sslCtx != null) {
                p.addLast(sslCtx.newHandler(ch.alloc()))
            }
            p.addLast("lengthDecoder",
                    LengthFieldBasedFrameDecoder(maxMessageLength, 0, 2, 0, 2))
            p.addLast("messageDecoder",  messageDecoder)
            p.addLast(messageHandlersProvider.getHandlers())
            p.addLast("lengthFieldPrepender", LengthFieldPrepender(2))
            p.addLast("messageEncoder", messageEncoder)
            p.addLast(AuthService.HandshakeServerHandler())
        }
    }

    interface MessageHandlerProvider {
        fun getHandlers(): List<MessageHandler>
    }

    interface MessageHandler : ChannelHandler {
        fun handleMessage(message: Message)
    }

    class MessageDecoder(private val serializer: MessageSerializer) : ByteToMessageDecoder() {
        override fun decode(ctx: ChannelHandlerContext, byteBuf: ByteBuf, out: MutableList<Any>) {
            if (byteBuf.readableBytes() > 0) {
                out.add(serializer.decode(byteBuf.readBytes(byteBuf.readableBytes())))
            }
        }
    }

    class ResponseEncoder(private val serializer: MessageSerializer) : MessageToByteEncoder<Message> {

    }
}