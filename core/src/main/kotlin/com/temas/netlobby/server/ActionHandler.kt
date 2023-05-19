package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

fun interface ActionHandler {
    fun handle(action: Action): Int
}

