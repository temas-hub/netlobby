package com.temas.netlobby.core

import java.util.*

sealed class Message
object Ping : Message()
object Pong : Message()
data class AuthRequest(val login: String) : Message()
data class SuccessAuthResponse(val login: String, val token: UUID) : Message()
data class FailedAuthResponse(val error: String) : Message()

sealed class AuthorizedMessage(val token: UUID) : Message()
class GetLobbyQueue(token: UUID) : AuthorizedMessage(token)
class GetLobbyQueueResponse(val lobbyId: String, token: UUID): AuthorizedMessage(token)