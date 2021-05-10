package com.temas.netlobby.client

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.config.serverModule
import com.temas.netlobby.core.ActionMessage
import com.temas.netlobby.core.Message
import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.status.*
import com.temas.netlobby.server.*
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.environmentProperties

class LocalServerBuilder {

    val actionHandlerModule = module {
        single(named("channelHandlers")) {
            emptyList<Message>()
        }
        single { actionHandler }
        single(named("updateBuilder")) {
            updateBuilder
        }
    }

    class Server(private val koin: Koin,
                private val updatePeriodMs: Long): NetLobbyServer, KoinComponent {
        val updateSender: UpdateSender by inject()
        val localSessionManager: LocalSessionManager by inject()
        val actionHandler: ActionHandler by inject()
        private lateinit var udpServer: UDPServer

        override fun getKoin(): Koin = this.koin

        override fun start() {
            udpServer = get()
            udpServer.init().sync()
            updateSender.start(updatePeriodMs)
        }

        override fun createLocalClient(inboundHandler: InboundHandler): NetLobbyClient {
            val session = localSessionManager.addSession(actionHandler, inboundHandler)
            return object : NetLobbyClient {
                override fun sendActions(actions: List<Action>) {
                    session.applyActions(actions)
                }
            }
        }

        override fun sendUpdates() {
            updateSender.sendUpdates()
        }

        override fun stop() {
            updateSender.stop()
            udpServer.destroy().sync()
        }

    }

    fun buildKoin(vararg modules: Module): Koin {
        val koinApp = koinApplication {
            printLogger(Level.ERROR)
            environmentProperties()
            modules(
                serializationModule,
                channelModule,
                *modules,
                actionHandlerModule
          )
        }
        ServerKoinContext.koinApp = koinApp
        return koinApp.koin
    }


    lateinit var actionHandler: ActionHandler
    lateinit var updateBuilder: UpdateBuilder
    private var updatePeriod: Long = 60

    fun withActionHandler(handler: ActionHandler) = apply {  this.actionHandler = handler }
    fun withUpdateBuilder(updateBuilder: UpdateBuilder) = apply { this.updateBuilder = updateBuilder }
    fun updatePeriod(ms: Long) = apply { this.updatePeriod = ms}

    fun build(): NetLobbyServer {
        val koin = buildKoin(serverModule)
        return Server(koin, updatePeriod);
    }
}