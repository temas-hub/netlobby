package com.temas.netlobby.server.communication

import com.temas.netlobby.server.ActionProcessor
import com.temas.netlobby.server.generator.PlayerIdGenerator
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress
import java.util.concurrent.ConcurrentHashMap

/**
 * Default implementation of registry for remote communication sessions. It uses concurrent hash map for storing sessions to reduce race conditions
 */
class ServerSessionRegistry(private val actionProcessor: ActionProcessor):
    SessionRegistry<InetSocketAddress, DatagramChannel> {
    private val sessionMap = ConcurrentHashMap<InetSocketAddress, CommunicationSession>()

    override fun addSession(key: InetSocketAddress, communication: DatagramChannel): CommunicationSession {
        return sessionMap.computeIfAbsent(key)
        { addr -> CommunicationSession(
            RemoteStateUpdateTransport(addr, communication),
            actionProcessor,
            PlayerIdGenerator.id)
        }
    }

    override fun listSessions(): List<CommunicationSession> {
        return sessionMap.values.toList()
    }

    override fun removeSession(key: InetSocketAddress) {
        sessionMap.remove(key)
    }


}