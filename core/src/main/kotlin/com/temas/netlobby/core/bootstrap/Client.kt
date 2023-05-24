package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.core.Connection
import com.temas.netlobby.core.api.NetLobbyClient
import com.temas.netlobby.core.net.udp.UDPClient
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * Created by azhdanov on 18.05.2023.
 */
class Client(private val koin: Koin): NetLobbyClient, KoinComponent {
    override fun getKoin(): Koin = this.koin
    private val networkClient: UDPClient = get()

    override fun connect(): Connection {
        return Connection(networkClient.connect().channel())
    }
}