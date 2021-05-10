package com.temas.netlobby.server

import com.temas.netlobby.core.IUserSession
import com.temas.netlobby.core.UserSession
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

class ServerSessionRegistry(val actionHandler: ActionHandler): SessionRegistry<DatagramChannel> {
    private val sessionMap = ConcurrentHashMap<InetSocketAddress, IUserSession>()

    override fun addSession(address: InetSocketAddress, channel: DatagramChannel): IUserSession {
        return sessionMap.computeIfAbsent(address)
        { addr -> UserSession(addr, channel, actionHandler, PlayerIdGenerator.id) }
    }

    override fun listSessions(): List<IUserSession> {
        return sessionMap.values.toList()
    }

}