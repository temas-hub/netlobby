package com.temas.netlobby.config

import com.temas.netlobby.core.net.udp.UdpUpstreamHandler
import com.temas.netlobby.core.net.udp.UDPClient
import com.temas.netlobby.core.*
import com.temas.netlobby.server.ServerSessionRegistry
import com.temas.netlobby.core.net.DefaultChannelInitializer
import com.temas.netlobby.core.net.MessageDecoder
import com.temas.netlobby.core.net.MessageEncoder
import com.temas.netlobby.core.net.udp.UDPServer
import com.temas.netlobby.server.*
import io.netty.channel.ChannelInitializer
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.nustaq.serialization.FSTConfiguration


val serializationModule = module {
    single { FSTConfiguration.createDefaultConfiguration() } bind FSTConfiguration::class
    factory { MessageSerializer(get()) }
    factory { MessageDecoder(get()) }
    factory { MessageEncoder(get()) }
}


val channelModule = module {

    factory {
        val sslCtx: SslContext? = if (getProperty("enableSSL","false").toBoolean()) {
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            null
        }
        DefaultChannelInitializer(sslCtx,
                upSteamHandler = get(),
                messageEncoder = get(),
                channelHandlers = get(named("channelHandlers"))
        )
    } bind ChannelInitializer::class

}

val clientModule = module {
    single { UdpUpstreamHandler(serializer = get()) } bind UdpUpstreamHandler::class

    single {
        UDPClient(
            get(),
            getProperty("server.host", "localhost"),
            getProperty("server.port", "17999").toInt()
        )
    }
}

val serverModule = module {
    single { ActionProcessor(get()) }
    single { ServerSessionRegistry(get()) } bind SessionRegistry::class
    single { UdpUpstreamHandler(sessionRegistry = get(),
                                serializer = get()) }
    single {  UpdateSender(
                sessionRegistry = get(),
                localSessionManager = get(),
                updateBuilder = get(named("updateBuilder"))) }
    single { LocalSessionManager(get()) }

    single {
        UDPServer(
            get(),
            getProperty("server.port", "17999").toInt()
        )
    }

}

