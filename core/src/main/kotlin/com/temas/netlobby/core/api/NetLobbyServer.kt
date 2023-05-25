package com.temas.netlobby.core.api

import com.temas.netlobby.core.model.InboundHandler


interface NetLobbyServer {

    fun start()

    fun createLocalClient(inboundHandler: InboundHandler): NetLobbyClient

    fun sendUpdates()

    fun stop()
}