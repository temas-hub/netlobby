package com.temas.netlobby.server

import com.temas.netlobby.core.UserSession
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

class ServerSessionRegistry: SessionRegistry<DatagramChannel> {
    private val sessionMap = ConcurrentHashMap<InetSocketAddress, UserSession>()

    override fun addSession(address: InetSocketAddress, channel: DatagramChannel) {
        sessionMap.computeIfAbsent(address)
        { addr -> UserSession(addr, channel) }

    }

    override fun listSessions(): List<UserSession> {
        return sessionMap.values.toList()
    }
}