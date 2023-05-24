package com.temas.netlobby.core.net.tcp

import com.temas.netlobby.core.Message
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel


/**
 * Holds and manages netty tcp client.
 * TODO: will be used for lobby management and authentication
 */
class TCPClient(private val channelInitializer: ChannelInitializer<SocketChannel>) {

    fun connect(host: String, port: Int): ChannelFuture {
        val group: EventLoopGroup = NioEventLoopGroup()
        val b = Bootstrap()
        b.group(group)
                .channel(NioSocketChannel::class.java)
                .handler(channelInitializer)

        // Make the connection attempt.
        return b.connect(host, port)
                .addListener { f -> if (!f.isSuccess) println(f.cause()) }
    }

    fun send(host: String, port: Int, msg : Message): ChannelFuture {
        val channelFuture = connect(host, port)
        return channelFuture.channel().writeAndFlush(msg)
    }
}