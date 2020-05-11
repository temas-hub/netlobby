package com.temas.netlobby.core

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import java.util.concurrent.CompletableFuture

class ClientComponent : KoinComponent {
    private val host: String = getKoin().getProperty("server.host", "localhost")
    private val port: Int = getKoin().getProperty("server.port", 17999)
    private val tcpClient: TCPClient by inject()
    private val responseAcceptor: ResponseAcceptor by inject()

    fun ping(): CompletableFuture<Pong> {
        tcpClient.init(host, port)
        tcpClient.send(Ping)
        return responseAcceptor.pongAcceptor()
    }
}

fun main() {
    startKoin() {
        printLogger()
        environmentProperties()
        modules(serializationModule, channelModule, clientModule)
    }

    ClientComponent().ping()
}