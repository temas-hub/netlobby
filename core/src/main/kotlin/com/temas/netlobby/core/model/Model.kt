package com.temas.netlobby.core.model

import java.io.Serializable

/**
 * Data transfer object for action performed be a gamer like move, attack, etc.
 */
data class Action(
    val type: ActionType,
    val data: ActionData? = null,
    val id: Int = generateId()
) : Serializable {
    companion object {
        var id = 0
        fun generateId()= ++id
    }
}

/**
 * Action type may be useful to group and filter actions on server side
 */
open class ActionType: Serializable
/**
 * Dummy action type for testing
 */
class DummyActionType: ActionType()

/**
 * Action data is a payload for action type
 */
open class ActionData: Serializable

/**
 * Data transfer object for server state to be sent to client
 */
data class ServerState(
    val playerId: Int,
    val serverTimestamp: Long,
    val lastActionId: Int,
    val serverData: ServerStateData
): Serializable

/**
 * Server state data is a payload for server state
 */
open class ServerStateData: Serializable
/**
 * Dummy server state data for testing
 */
class DummyModel: ServerStateData()


typealias UpdateBuilder = () -> ServerStateData

typealias InboundHandler = (state: ServerState) -> Unit
