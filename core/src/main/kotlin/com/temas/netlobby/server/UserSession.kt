package com.temas.netlobby.core.server

import com.temas.netlobby.core.IUserSession
import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.TransferableMessage
import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData
import com.temas.netlobby.server.ActionProcessor
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress

abstract class AbstractUserSession(
    val playerId: Int,
    protected val actionProcessor: ActionProcessor
): IUserSession

class UserSession(
    private val address: InetSocketAddress,
    private val channel: DatagramChannel,
    actionProcessor: ActionProcessor,
    playerId: Int
): AbstractUserSession(playerId, actionProcessor) {

    override fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
        val state = ServerState(playerId, timestamp, actionProcessor.lastActionId, stateData)
        val statusMessage = TransferableMessage(address, StateMessage(state))
        channel.writeAndFlush(statusMessage)
    }

    override fun applyActions(actions: List<Action>) {
        actionProcessor.process(actions)
    }
}
