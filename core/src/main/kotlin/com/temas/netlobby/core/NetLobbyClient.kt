package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action

interface NetLobbyClient {
    suspend fun connect(): Connection
}