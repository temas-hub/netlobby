package com.temas.netlobby.core.net.udp

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress

class UDPClient(private val channelInitializer: ChannelInitializer<DatagramChannel>,
                private val defaultHost: String,
                private val defaultPort: Int) {


    fun connect(host: String? = null, port: Int? = null): ChannelFuture {
        val group: EventLoopGroup = NioEventLoopGroup()
        val udpBootstrap = Bootstrap()
        udpBootstrap.group(group).channel(NioDatagramChannel::class.java)
                .handler(channelInitializer)
        val channelFuture = udpBootstrap.connect(InetSocketAddress(host ?: defaultHost, port ?: defaultPort))
        return channelFuture
                .addListener { f -> if (!f.isSuccess) println(f.cause()) }
    }

}