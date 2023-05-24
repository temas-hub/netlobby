package com.temas.netlobby.core.net.udp

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress

/**
 * Holds and manages netty udp client
 */
class UDPClient(private val channelInitializer: ChannelInitializer<DatagramChannel>,
                private val defaultHost: String,
                private val defaultPort: Int) {


    /**
     * Init netty client and connects to server, returning channel future.
     * Async operation, use channelFuture.addListener to get result
     * @param host if null default host will be used
     * @param port if null default port will be used
     */
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