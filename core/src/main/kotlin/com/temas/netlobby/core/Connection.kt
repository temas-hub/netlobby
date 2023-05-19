package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action
import io.netty.channel.Channel

class Connection(private val channel: Channel): IConnection {
    override fun sendActions(actions: List<Action>) {
        val message = TransferableMessage(data = ActionMessage(actions))
        channel.writeAndFlush(message).await()
    }
}