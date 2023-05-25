package com.temas.netlobby.core.net

import com.temas.netlobby.core.api.IConnection
import com.temas.netlobby.core.model.Action
import com.temas.netlobby.core.model.ActionMessage
import com.temas.netlobby.core.model.TransferableMessage
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