package com.temas.netlobby.server.communication

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import com.temas.netlobby.core.status.ServerStateData
import com.temas.netlobby.server.ActionProcessor

/**
 * Communication session for each particular player holding:
 *  - transport for sending state updates to the client. It is important to have separate transport for each client
 *  - action processor for processing actions from the client on server. Can be a common processor for all clients
 */
class CommunicationSession(
    private val stateTransport: StateUpdateTransport,
    protected val actionProcessor: ActionProcessor,
    private val playerId: Int
) {
    protected var lastActionId: Int = -1

    /**
     *  Send server state update to client
     *  @param stateData server state data to send
     *  @param timestamp server timestamp when the state was captured
     */
    fun sendStateUpdate(stateData: ServerStateData, timestamp: Long) {
        val state = ServerState(playerId, timestamp, lastActionId, stateData)
        stateTransport.send(state)
    }

    /**
     *  Apply actions from client to server
     *  @param actions list of actions to apply
     */
    fun applyActions(actions: List<Action>) {
        val processedIds = actionProcessor.process(actions)
        if (processedIds.isNotEmpty()) {
            lastActionId = processedIds.last()
        }
    }


}
