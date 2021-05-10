package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.ServerState
import java.io.Serializable
import java.net.InetSocketAddress
import java.util.*


sealed class Message : Serializable
object Ping : Message()
object Pong : Message()
class TransferableMessage(val address: InetSocketAddress? = null, val data: Message): Message(), Serializable {
    override fun toString(): String {
        return "Address=$address.Data=$data";
    }
}
data class AuthRequest(val login: String) : Message()
sealed class AuthResponse(val login: String): Message()
class SuccessAuthResponse(login: String, val sessionId: UUID) : AuthResponse(login)
class FailedAuthResponse(login: String, val error: String) : AuthResponse(login)
data class StateMessage(val state: ServerState): Message()

data class ActionMessage(val actions: List<Action>): Message()

sealed class AuthorizedMessage(val sessionId: UUID) : Message()
class GetLobbyQueue(sessionId: UUID) : AuthorizedMessage(sessionId)
class LobbySuccessAwait(val waitSeconds: Int, sessionId: UUID): AuthorizedMessage(sessionId)
class LobbySuccessResponse(val lobbyId: String, sessionId: UUID): AuthorizedMessage(sessionId)