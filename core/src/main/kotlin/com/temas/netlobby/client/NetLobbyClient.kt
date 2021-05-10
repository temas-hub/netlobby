package com.temas.netlobby.client

import com.temas.netlobby.core.status.Action

interface NetLobbyClient {
    fun sendActions(actions: List<Action>)
}