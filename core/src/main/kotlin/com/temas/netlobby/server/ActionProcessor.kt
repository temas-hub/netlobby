package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

class ActionProcessor(private val actionHandler: ActionHandler) {

    fun process(actions: List<Action>): List<Int> {
        return actions.map {
            actionHandler.handle(it)
        }
    }
}