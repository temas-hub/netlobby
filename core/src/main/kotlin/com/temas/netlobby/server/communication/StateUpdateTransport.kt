package com.temas.netlobby.server.communication

import com.temas.netlobby.core.model.ServerState
import com.temas.netlobby.core.model.StateMessage
import com.temas.netlobby.core.model.TransferableMessage
import java.net.InetSocketAddress
import io.netty.channel.socket.DatagramChannel

/**
 * Transport for sending server state updates to clients.
 */
interface StateUpdateTransport {
    /**
     * Send server state to remote client
     * @param state server state to send
     */
    fun send(state: ServerState)
}

/**
 * Default implementation of state update transport for remote clients
 * @param address of remote client. It is used as recipient address for message
 * @param channel to send message
 */
class RemoteStateUpdateTransport(private val address: InetSocketAddress,
                                 private val channel: DatagramChannel
): StateUpdateTransport {
    /**
     * Send server state to remote client
     */
    override fun send(state: ServerState) {
        val statusMessage = TransferableMessage(address, StateMessage(state))
        channel.writeAndFlush(statusMessage)
    }

}