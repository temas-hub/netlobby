package com.temas.netlobby.server

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel

class UDPServer(private val channelInitializer: ChannelInitializer<DatagramChannel>,
                private val defaultPort: Int) {


    fun init(port: Int? = null): ChannelFuture {
        val bossGroup: EventLoopGroup = NioEventLoopGroup(1)
        val bootstrap = Bootstrap()
        return bootstrap.group(bossGroup)
                .channel(NioDatagramChannel::class.java)
                .handler(channelInitializer)
                .bind(port ?: defaultPort)
    }
}