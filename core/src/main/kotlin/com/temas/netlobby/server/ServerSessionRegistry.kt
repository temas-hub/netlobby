package com.temas.netlobby.server

import com.temas.netlobby.core.server.UserSession
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

class ServerSessionRegistry(val actionProcessor: ActionProcessor): SessionRegistry<DatagramChannel> {
    private val sessionMap = ConcurrentHashMap<InetSocketAddress, UserSession>()

    override fun addSession(address: InetSocketAddress, channel: DatagramChannel): UserSession {
        return sessionMap.computeIfAbsent(address)
        { addr -> UserSession(
            RemoteStateUpdateTransport(addr, channel),
            actionProcessor,
            PlayerIdGenerator.id)
        }
    }

    override fun listSessions(): List<UserSession> {
        return sessionMap.values.toList()
    }

}