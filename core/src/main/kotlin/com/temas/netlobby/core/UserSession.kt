package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData
import com.temas.netlobby.server.ActionHandler
import io.netty.channel.socket.DatagramChannel
import java.net.InetSocketAddress
import kotlin.math.max

abstract class AbstractUserSession(val playerId: Int,
                          val actionHandler: ActionHandler): IUserSession {
    protected val actionProcessor = ActionProcessor(actionHandler)
}

class UserSession(
    val address: InetSocketAddress,
    val channel: DatagramChannel,
    actionHandler: ActionHandler,
    playerId: Int
): AbstractUserSession(playerId, actionHandler) {

    override fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
        val state = ServerState(playerId, timestamp, actionProcessor.lastActionId, stateData)
        val statusMessage = TransferableMessage(address, StateMessage(state))
        channel.writeAndFlush(statusMessage)
    }

    override fun applyActions(actions: List<Action>) {
        actionProcessor.process(actions)
    }
}


class ActionProcessor(private val actionHandler: ActionHandler) {
    var lastActionId: Int = -1;

    fun process(actions: List<Action>) {
        actions.forEach {
            actionHandler.handle(it)
            actionProcessed(it)
        }
    }


    fun actionProcessed(action: Action)  {
        lastActionId = max(lastActionId, action.id)
    }

}