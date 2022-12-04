package com.temas.netlobby.server

import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.TransferableMessage
import com.temas.netlobby.core.status.ServerState
import java.net.InetSocketAddress
import io.netty.channel.socket.DatagramChannel

interface StateUpdateTransport {
    fun send(state: ServerState)
}

class RemoteStateUpdateTransport(private val address: InetSocketAddress,
                                 private val channel: DatagramChannel
): StateUpdateTransport {
    override fun send(state: ServerState) {
        val statusMessage = TransferableMessage(address, StateMessage(state))
        channel.writeAndFlush(statusMessage)
    }

}