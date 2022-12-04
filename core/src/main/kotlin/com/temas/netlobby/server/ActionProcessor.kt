package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

class ActionProcessor(private val actionHandler: ActionHandler) {

    fun process(actions: List<Action>) {
        actions.forEach {
            actionHandler.handle(it)
        }
    }
}