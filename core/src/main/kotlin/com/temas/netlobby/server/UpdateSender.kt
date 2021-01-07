package com.temas.netlobby.server

import com.temas.netlobby.core.StatusMessage
import com.temas.netlobby.core.TransferableMessage
import com.temas.netlobby.core.status.Status
import java.util.*
import kotlin.concurrent.schedule

class UpdateSender(private val sessionRegistry: ServerSessionRegistry) {

    init {
        Timer("Update sender", false).schedule(0,1000) {
            sendUpdates()
        }
    }

    fun sendUpdates() {
        sessionRegistry.listSessions().forEach {
            val statusMessage = TransferableMessage(it.address, StatusMessage(Status(System.currentTimeMillis())))
            it.sendMessage(statusMessage)
        }
    }
}