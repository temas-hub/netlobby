package com.temas.netlobby.server

import com.temas.netlobby.core.model.Action


/**
 * A default processor for game actions which processes them sequentially.
 */
class ActionProcessor(private val actionHandler: ActionHandler) {

    /**
     * Processes actions sequentially and returns a list of processed action ids.
     */
    fun process(actions: List<Action>): List<Int> {
        return actions.map {
            actionHandler.handle(it)
        }
    }
}