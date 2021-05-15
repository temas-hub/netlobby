package com.temas.netlobby.server

import com.temas.netlobby.core.IUserSession
import com.temas.netlobby.core.server.AbstractUserSession
import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData

class LocalSessionManager(private val actionProcessor: ActionProcessor,) {

    private val sessionList = mutableListOf<IUserSession>()

    inner class LocalSession(private val inboundHandler: InboundHandler)
        : AbstractUserSession(PlayerIdGenerator.id, actionProcessor) {

        override fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
            val state = ServerState(playerId, timestamp, actionProcessor.lastActionId, stateData)
            inboundHandler.invoke(state)
        }

        override fun applyActions(actions: List<Action>) {
            actionProcessor.process(actions)
        }
    }

    fun addSession(inboundHandler: InboundHandler): IUserSession {
        val session = LocalSession(inboundHandler)
        sessionList.add(session)
        return session
    }

    fun listSessions(): List<IUserSession> {
        return sessionList
    }
}