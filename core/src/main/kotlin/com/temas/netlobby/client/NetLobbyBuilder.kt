package com.temas.netlobby.client

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import com.temas.netlobby.core.ActionMessage
import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.status.*
import com.temas.netlobby.server.DefaultActionHandler
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.environmentProperties
import java.util.*
import kotlin.concurrent.schedule

class NetLobbyBuilder {

    lateinit var inboundHandler: (state: ServerState) -> Unit

    class Client(private val koin: Koin): NetLobbyClient, KoinComponent {
        override fun getKoin(): Koin = this.koin


        override fun sendActions(actions: List<Action>) {
            val channel: Channel = get<UDPClient>().connect().channel()
            channel.writeAndFlush(ActionMessage(actions))
        }
    }

    fun withLocalServer() = LocalServerBuilder()

    fun withInboundHandler(inboundHandler: (state: ServerState) -> Unit) = apply { this.inboundHandler = inboundHandler }

    private fun handler(clientUpdateHandler: InboundHandler) =
            object : SimpleChannelInboundHandler<StateMessage>() {
                override fun channelRead0(ctx: ChannelHandlerContext, msg: StateMessage) {
                    clientUpdateHandler(msg.state);
                }
            }

    fun build(): NetLobbyClient {
        val koinApp = koinApplication {
            printLogger(Level.ERROR)
            environmentProperties()
            modules(
                serializationModule,
                channelModule,
                clientModule,
                module {
                        factory(named("channelHandlers")) {
                            listOf(handler(inboundHandler))
                        }
                 }
            )
        }
        ClientKoinContext.koinApp = koinApp
        return Client(koinApp.koin)
    }


}

fun main() {
    // TODO. Unit test
    //  1. Create server and local client.
    //  2. Send an action
    //  3. Rebuild model
    //  4. Send server update
    //  5. Print the update
//    var model: Long = 0
//    data class TestData(val modelNumber: Long): ServerStateData()
//
//    val serverBuilder = LocalServerBuilder()
//    val server = serverBuilder
//        .withActionHandler {
//            model++
//            println(it)
//        }
//        .withUpdateBuilder { TestData(model) }
//        .updatePeriod(0)
//        .build();
//
//    server.start()
//
//    val client = server.createLocalClient { println((it)) }
//    class SampleActionType : ActionType()
//    client.sendActions(listOf(Action(SampleActionType())))
//    server.sendUpdates()
//

    var model: Long = 0
    data class TestData(val modelNumber: Long): ServerStateData()
    val serverBuilder = LocalServerBuilder()
    val server = serverBuilder
        .withActionHandler {
            model++
            println(it)
        }
        .withUpdateBuilder { TestData(model) }
        .updatePeriod(300)
        .build();

    server.start()
}