package com.temas.netlobby.server

import com.temas.netlobby.core.model.Action


/**
 *  Server side handler for game actions like move, attack, etc.
 */
fun interface ActionHandler {
    /**
     * Handles action and returns id of processed action.
     */
    fun handle(action: Action): Int
}

