package com.temas.netlobby.server

import com.temas.netlobby.core.server.UserSession
import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData

class LocalSessionManager(private val actionProcessor: ActionProcessor,) {

    private val sessionList = mutableListOf<UserSession>()

    class LocalStateTransport(val inboundHandler: InboundHandler) : StateUpdateTransport {
        override fun send(state: ServerState) {
            inboundHandler.invoke(state)
        }
    }

    fun addSession(inboundHandler: InboundHandler): UserSession {
        val session = UserSession(
            LocalStateTransport(inboundHandler),
            actionProcessor,
            PlayerIdGenerator.id)
        sessionList.add(session)
        return session
    }

    fun listSessions(): List<UserSession> {
        return sessionList
    }
}