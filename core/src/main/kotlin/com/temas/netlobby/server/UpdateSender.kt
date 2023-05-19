package com.temas.netlobby.server

import com.temas.netlobby.core.status.UpdateBuilder

class UpdateSender(
    private val sessionRegistry: ServerSessionRegistry,
    private val localSessionManager: LocalSessionManager,
    private val updateBuilder: UpdateBuilder,
) {

    fun sendUpdates() {
        val state = updateBuilder.invoke()
        val timeStamp = System.currentTimeMillis()
        (sessionRegistry.listSessions() + localSessionManager.listSessions()).forEach {
            it.sendStateUpdate(state, timeStamp)
        }
    }

}