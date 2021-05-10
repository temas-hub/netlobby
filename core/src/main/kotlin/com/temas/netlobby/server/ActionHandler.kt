package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

fun interface ActionHandler {
    fun handle(action: Action)
}

abstract class DefaultActionHandler: ActionHandler {
    override fun handle(action: Action) {
        handleImpl(action);
    }
    abstract fun handleImpl(action: Action)
}
