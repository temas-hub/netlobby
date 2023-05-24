package com.temas.netlobby.core.net.udp

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel

/**
 * Holds and manages netty udp server
 */
class UDPServer(private val channelInitializer: ChannelInitializer<DatagramChannel>,
                private val defaultPort: Int) {

    private lateinit var bossGroup: EventLoopGroup
    private lateinit var channel: ChannelFuture

    /**
     * Initializes server and open port for listening
     * @param port if null default port will be used
     */
    fun init(port: Int? = null): ChannelFuture {
        bossGroup = NioEventLoopGroup(1)
        val bootstrap = Bootstrap()
        channel = bootstrap.group(bossGroup)
                .channel(NioDatagramChannel::class.java)
                .handler(channelInitializer)
                .bind(port ?: defaultPort)
        return channel
    }

    /**
     * Closes server and frees resources
     */
    fun destroy(): ChannelFuture {
        return channel.channel().close().addListener {
            bossGroup.shutdownGracefully()
        }
    }
}