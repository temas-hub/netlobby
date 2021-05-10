package com.temas.netlobby.server

import com.temas.netlobby.client.NetLobbyClient
import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.core.status.ServerState

interface NetLobbyServer {

    fun start()

    fun createLocalClient(inboundHandler: InboundHandler): NetLobbyClient

    fun sendUpdates()

    fun stop()
}