package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.config.buildKoinApplication
import com.temas.netlobby.config.serverModule
import com.temas.netlobby.core.api.NetLobbyServer
import com.temas.netlobby.core.model.*
import com.temas.netlobby.server.*
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Server builder uses koin for dependency injection
 */
class ServerBuilder {

    private lateinit var actionHandler: ActionHandler
    private lateinit var updateBuilder: UpdateBuilder
    private var updatePeriod: Long = 60

    private fun actionHandlerModule(): Module {
        return module {
            single(named("channelHandlers")) {
                emptyList<Message>()
            }
            single { actionHandler }
            single(named("updateBuilder")) {
                updateBuilder
            }
        }
    }

    fun withActionHandler(handler: ActionHandler) = apply {  this.actionHandler = handler }
    fun withUpdateBuilder(updateBuilder: UpdateBuilder) = apply { this.updateBuilder = updateBuilder }
    fun updatePeriod(ms: Long) = apply { this.updatePeriod = ms}

    fun build(): NetLobbyServer {
        val koinApp = buildKoinApplication(
            serverModule,
            actionHandlerModule()
        )
        ServerKoinContext.koinApp = koinApp
        return Server(koinApp.koin, updatePeriod)
    }
}

fun main() {
    var model: Long = 0

    val server = ServerBuilder()
        .withActionHandler {
            model++
            println(it)
            it.id
        }
        .withUpdateBuilder { DummyModel() }
        .updatePeriod(300)
        .build()

    server.start()
    val client = server.createLocalClient { println((it)) }

    val connection = client.connect()

    class SampleActionType : ActionType()
    connection.sendActions(listOf(Action(SampleActionType())))
}