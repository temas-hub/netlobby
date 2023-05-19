package com.temas.netlobby.core.bootstrap

import com.temas.netlobby.config.buildKoinApplication
import com.temas.netlobby.config.serverModule
import com.temas.netlobby.core.*
import com.temas.netlobby.core.status.*
import com.temas.netlobby.server.*
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ApplicationBuilder {
    lateinit var actionHandler: ActionHandler
    lateinit var updateBuilder: UpdateBuilder

    fun withActionHandler(handler: ActionHandler) = apply {  this.actionHandler = handler }
    fun withUpdateBuilder(updateBuilder: UpdateBuilder) = apply { this.updateBuilder = updateBuilder }

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

    fun buildKoin(): KoinApplication {
        val koinApp = buildKoinApplication(
                serverModule,
                actionHandlerModule()
            )
        ServerKoinContext.koinApp = koinApp
        return koinApp
    }
}


class ServerBuilder {

    private lateinit var koinApplication: KoinApplication
    private var updatePeriod: Long = 60
    fun updatePeriod(ms: Long) = apply { this.updatePeriod = ms}
    fun withApplication(koinApplication: KoinApplication) = apply { this.koinApplication = koinApplication }

    fun build(): NetLobbyServer {
        return Server(koinApplication.koin, updatePeriod)
    }
}

fun main() {
    var model: Long = 0

    val app = ApplicationBuilder()
        .withActionHandler {
            model++
            println(it)
            it.id
        }
        .withUpdateBuilder { DummyModel() }
        .buildKoin()


    val server = ServerBuilder()
        .withApplication(app)
        .updatePeriod(300)
        .build()

    server.start()
    val client = server.createLocalClient { println((it)) }

    val connection = client.connect()

    class SampleActionType : ActionType()
    connection.sendActions(listOf(Action(SampleActionType())))
}