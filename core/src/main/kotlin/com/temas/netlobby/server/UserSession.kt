package com.temas.netlobby.core.server

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData
import com.temas.netlobby.server.ActionProcessor
import com.temas.netlobby.server.StateUpdateTransport


class UserSession(
    private val stateTransport: StateUpdateTransport,
    protected val actionProcessor: ActionProcessor,
    private val playerId: Int
) {
    protected var lastActionId: Int = -1

    fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
        val state = ServerState(playerId, timestamp, lastActionId, stateData)
        stateTransport.send(state)
    }

    fun applyActions(actions: List<Action>) {
        val processedIds = actionProcessor.process(actions)
        if (processedIds.isNotEmpty()) {
            lastActionId = processedIds.last()
        }
    }


}
