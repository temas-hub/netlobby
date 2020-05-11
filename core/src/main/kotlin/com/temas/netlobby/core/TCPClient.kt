package com.temas.netlobby.core

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.io.IOException


class TCPClient(private val channelInitializer: ChannelInitializer<SocketChannel>) {

    private lateinit var channel : Channel

    fun init(host: String, port: Int) {
        val group: EventLoopGroup = NioEventLoopGroup()
        try {
            val b = Bootstrap()
            b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(channelInitializer)

            // Make the connection attempt.
            channel = b.connect(host, port).sync().channel()
            channel.closeFuture().sync()
        } catch (e : IOException) {
            //TODO
            println(e)
        } finally {
            group.shutdownGracefully();
        }
    }

    fun send(msg : Message): ChannelFuture {
        return channel.writeAndFlush(msg)
    }
}