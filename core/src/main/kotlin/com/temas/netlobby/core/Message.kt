package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import java.io.Serializable
import java.net.InetSocketAddress

/**
 * Message to send between server and client
 */
sealed class Message : Serializable

/**
 * Addressable wrapper for message.
 * @param address of recipient. It is mandatory for sending message from server to client because server can have multiple clients
 * The address may be null for messages from client to server because client maintain a server channel with the address
 * @param data message to send
 */
class TransferableMessage(val address: InetSocketAddress? = null, val data: Message): Message(), Serializable {
    override fun toString(): String {
        return "Address=$address.Data=$data"
    }
}

/**
 * Server state message
 */
data class StateMessage(val state: ServerState): Message()

/**
 * Player action message
 */
data class ActionMessage(val actions: List<Action>): Message()