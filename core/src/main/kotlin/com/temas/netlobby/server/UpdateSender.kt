package com.temas.netlobby.server

import com.temas.netlobby.core.status.UpdateBuilder
import java.util.*
import kotlin.concurrent.schedule

class UpdateSender(private val sessionRegistry: ServerSessionRegistry,
                   private val localSessionManager: LocalSessionManager,
                   private val updateBuilder: UpdateBuilder
) {
    var timerTask: TimerTask? = null
    fun start(updatePeriodMs: Long) {
        if (updatePeriodMs > 0) {
            timerTask = Timer("Update sender", false).schedule(0, updatePeriodMs) {
                sendUpdates()
            }
        }
    }

    fun sendUpdates() {
        val state = updateBuilder.invoke()
        val timeStamp = System.currentTimeMillis()
        (sessionRegistry.listSessions() + localSessionManager.listSessions()).forEach {
            it.sendStateUpdate(state, timeStamp)
        }
    }

    fun stop() {
        timerTask?.cancel()
    }
}