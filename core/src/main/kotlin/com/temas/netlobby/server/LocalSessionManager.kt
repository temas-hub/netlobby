package com.temas.netlobby.server

import com.temas.netlobby.core.AbstractUserSession
import com.temas.netlobby.core.IUserSession
import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData

class LocalSessionManager {

    private val sessionList = mutableListOf<IUserSession>()

    inner class LocalSession(actionHandler: ActionHandler,
                             private val inboundHandler: InboundHandler)
        : AbstractUserSession(PlayerIdGenerator.id, actionHandler) {

        override fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
            val state = ServerState(playerId, timestamp, actionProcessor.lastActionId, stateData)
            inboundHandler.invoke(state)
        }

        override fun applyActions(actions: List<Action>) {
            actions.forEach { actionHandler.handle(it) }
        }

    }

    fun addSession(actionHandler: ActionHandler,
                   inboundHandler: InboundHandler): IUserSession {
        val session = LocalSession(actionHandler, inboundHandler)
        sessionList.add(session)
        return session
    }

    fun listSessions(): List<IUserSession> {
        return sessionList
    }
}