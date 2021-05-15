package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action

interface NetLobbyClient {
    fun sendActions(actions: List<Action>)
}