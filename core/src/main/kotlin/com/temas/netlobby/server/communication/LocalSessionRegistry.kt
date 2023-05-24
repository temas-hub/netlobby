package com.temas.netlobby.server.communication

import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.server.ActionProcessor
import com.temas.netlobby.server.generator.PlayerIdGenerator

/**
 * Session registry holds communication sessions. Local session manager is used for local sessions
 */
class LocalSessionRegistry(private val actionProcessor: ActionProcessor): SessionRegistry<Int, InboundHandler> {

    private val sessions = mutableMapOf<Int, CommunicationSession>()

    class LocalStateTransport(private val inboundHandler: InboundHandler) : StateUpdateTransport {
        override fun send(state: ServerState) {
            inboundHandler.invoke(state)
        }
    }

    /**
     * Register a new session to the manager
     */
    override fun addSession(key: Int, communication: InboundHandler): CommunicationSession {
        val session = CommunicationSession(
            LocalStateTransport(communication),
            actionProcessor,
            PlayerIdGenerator.id)
        sessions[key] = session
        return session
    }

    /**
     * List all registered sessions
     */
    override fun listSessions(): List<CommunicationSession> {
        return sessions.values.toList()
    }

    override fun removeSession(key: Int) {
        sessions.remove(key)
    }
}