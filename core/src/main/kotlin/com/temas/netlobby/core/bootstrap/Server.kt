package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.core.IConnection
import com.temas.netlobby.core.NetLobbyClient
import com.temas.netlobby.core.NetLobbyServer
import com.temas.netlobby.core.net.udp.UDPServer
import com.temas.netlobby.core.status.Action
import com.temas.netlobby.core.status.InboundHandler
import com.temas.netlobby.server.LocalSessionManager
import com.temas.netlobby.server.TimerService
import com.temas.netlobby.server.UpdateSender
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class Server(private val koin: Koin,
             private val updatePeriodMs: Long): NetLobbyServer, KoinComponent {
    val timerService: TimerService by inject()
    val updateSender: UpdateSender by inject()
    val localSessionManager: LocalSessionManager by inject()
    private lateinit var udpServer: UDPServer

    override fun getKoin(): Koin = this.koin

    override fun start() {
        udpServer = get()
        udpServer.init().sync()
        timerService.start(updatePeriodMs)
    }

    override fun createLocalClient(inboundHandler: InboundHandler): NetLobbyClient {
        val session = localSessionManager.addSession(inboundHandler)
        return object : NetLobbyClient {
            override fun connect(): IConnection {
                return object : IConnection {
                    override fun sendActions(actions: List<Action>) {
                        session.applyActions(actions)
                    }
                }
            }
        }
    }

    override fun sendUpdates() {
        updateSender.sendUpdates()
    }

    override fun stop() {
        timerService.stop()
        udpServer.destroy().sync()
    }

}