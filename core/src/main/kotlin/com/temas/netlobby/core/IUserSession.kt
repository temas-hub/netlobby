package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData

interface IUserSession {
    fun sendStateUpdate(stateData: ServerStateData, timestamp: Long = 0)

    fun applyActions(actions: List<Action>)
}