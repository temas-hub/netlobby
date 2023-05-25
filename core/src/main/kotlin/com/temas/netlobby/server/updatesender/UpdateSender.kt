package com.temas.netlobby.server.updatesender

import com.temas.netlobby.core.model.UpdateBuilder
import com.temas.netlobby.server.communication.LocalSessionRegistry
import com.temas.netlobby.server.communication.ServerSessionRegistry

/**
 * Aggregates local and remote sessions and sends server state updates to all those sessions
 */
class UpdateSender(
    private val sessionRegistry: ServerSessionRegistry,
    private val localSessionRegistry: LocalSessionRegistry,
    private val updateBuilder: UpdateBuilder
) {

    fun sendUpdates() {
        val state = updateBuilder.invoke()
        val timeStamp = System.currentTimeMillis()
        (sessionRegistry.listSessions() + localSessionRegistry.listSessions()).forEach {
            it.sendStateUpdate(state, timeStamp)
        }
    }

}