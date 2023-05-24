package com.temas.netlobby.core

import com.temas.netlobby.core.api.IConnection
import com.temas.netlobby.core.status.Action
import io.netty.channel.Channel

/**
 * Default implementation of connection to server.
 * It wraps action list into TransferableMessage and sends it into configured channel
 */
class Connection(private val channel: Channel): IConnection {
    override fun sendActions(actions: List<Action>) {
        val message = TransferableMessage(data = ActionMessage(actions))
        channel.writeAndFlush(message).await()
    }
}