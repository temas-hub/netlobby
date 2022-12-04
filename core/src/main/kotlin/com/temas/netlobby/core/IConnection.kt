package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action

interface IConnection {
    suspend fun sendActions(actions: List<Action>)
}