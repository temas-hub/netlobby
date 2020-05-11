package com.temas.netlobby.core

import java.util.*

sealed class Message
object Ping : Message()
object Pong : Message()
data class AuthRequest(val login: String) : Message()
data class SuccessAuthResponse(val login: String, val sessionId: UUID) : Message()
data class FailedAuthResponse(val error: String) : Message()

sealed class AuthorizedMessage(val sessionId: UUID) : Message()
class GetLobbyQueue(sessionId: UUID) : AuthorizedMessage(sessionId)
class LobbySuccessAwait(val waitSeconds: Int, sessionId: UUID): AuthorizedMessage(sessionId)
class LobbySuccessResponse(val lobbyId: String, sessionId: UUID): AuthorizedMessage(sessionId)