package com.temas.netlobby.client

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ActionType

fun main() {
    val client = NetLobbyBuilder()
        .withInboundHandler { println((it)) }
        .build()

    class SampleActionType : ActionType()
    client.sendActions(listOf(Action(SampleActionType())))
}