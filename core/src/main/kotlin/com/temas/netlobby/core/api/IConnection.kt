package com.temas.netlobby.core.api

import com.temas.netlobby.core.model.Action


/**
 * Connection to send game actions to server
 * @see com.temas.netlobby.core.model.Action
 */
interface IConnection {

    /**
     * Send list of actions to server. It is assumed the action will be applied in the order the appeared in the list
     * Sync operation.
     * @param actions list of actions to send
     */
    fun sendActions(actions: List<Action>)
}