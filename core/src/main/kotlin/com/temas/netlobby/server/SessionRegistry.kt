package com.temas.netlobby.server

import com.temas.netlobby.core.server.UserSession
import java.net.InetSocketAddress

interface SessionRegistry<T> {

    fun addSession(address: InetSocketAddress, channel: T): UserSession

    fun listSessions(): List<UserSession>
}