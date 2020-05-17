package com.temas.netlobby.core

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import io.netty.channel.Channel
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import java.util.concurrent.CompletableFuture

class ClientComponent : KoinComponent {
    private val host: String = getKoin().getProperty("server.host", "localhost")
    private val port: Int = getKoin().getProperty("server.port", 17999)
    private val tcpClient: TCPClient by inject()
    private val requestManager: RequestManager by inject()

    fun ping(): CompletableFuture<Pong> {
        val future = tcpClient.send(host, port, Ping)
        return requestManager.pongAcceptor()
    }

    fun login(serverHost: String, serverPort: Int, email: String): CompletableFuture<Session> {
        tcpClient.connect(serverHost, serverPort)
                .addListener {f ->
                    tcpClient.sendLogin(f.get() as Channel, AuthRequest(email))
                }

        return requestManager.loginResponse()
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