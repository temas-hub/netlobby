package com.temas.netlobby.core

import java.util.*

sealed class Message
object Ping : Message()
object Pong : Message()
data class AuthRequest(val login: String) : Message()
sealed class AuthResponse(val login: String): Message()
class SuccessAuthResponse(login: String, val sessionId: UUID) : AuthResponse(login)
class FailedAuthResponse(login: String, val error: String) : AuthResponse(login)

sealed class AuthorizedMessage(val sessionId: UUID) : Message()
class GetLobbyQueue(sessionId: UUID) : AuthorizedMessage(sessionId)
class LobbySuccessAwait(val waitSeconds: Int, sessionId: UUID): AuthorizedMessage(sessionId)
class LobbySuccessResponse(val lobbyId: String, sessionId: UUID): AuthorizedMessage(sessionId)