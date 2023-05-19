package com.temas.netlobby.core.status

import java.io.Serializable

data class Action(
    val type: ActionType,
    val data: ActionData? = null,
    val id: Int = generateId()) : Serializable {
    companion object {
        var id = 0
        fun generateId()= ++id
    }
}

open class ActionType: Serializable
class DummyActionType: ActionType()
open class ActionData: Serializable

data class ServerState(
    val playerId: Int,
    val serverTimestamp: Long,
    val lastActionId: Int,
    val serverData: ServerStateData
): Serializable

open class ServerStateData: Serializable
class DummyModel: ServerStateData()


typealias UpdateBuilder = () -> ServerStateData

typealias InboundHandler = (state: ServerState) -> Unit
