package com.temas.netlobby.server

import com.temas.netlobby.core.UserSession
import java.net.InetSocketAddress

interface SessionRegistry<T> {

    fun addSession(address: InetSocketAddress, channel: T)

    fun listSessions(): List<UserSession>
}