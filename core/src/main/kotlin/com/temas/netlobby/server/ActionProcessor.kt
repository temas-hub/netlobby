package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action
import kotlin.math.max

class ActionProcessor(private val actionHandler: ActionHandler) {
    var lastActionId: Int = -1;

    fun process(actions: List<Action>) {
        actions.forEach {
            actionHandler.handle(it)
            actionProcessed(it)
        }
    }

    private fun actionProcessed(action: Action)  {
        lastActionId = max(lastActionId, action.id)
    }

}