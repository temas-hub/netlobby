package com.temas.netlobby.core

import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress

class UserSession(val address: InetSocketAddress, val channel: DatagramChannel) {
    fun sendMessage(message: Message) {
        channel.writeAndFlush(message);
    }
}