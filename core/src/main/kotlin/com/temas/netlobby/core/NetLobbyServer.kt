package com.temas.netlobby.core

import com.temas.netlobby.core.NetLobbyClient
import com.temas.netlobby.core.status.InboundHandler

interface NetLobbyServer {

    fun start()

    fun createLocalClient(inboundHandler: InboundHandler): NetLobbyClient

    fun sendUpdates()

    fun stop()
}