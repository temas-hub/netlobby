package com.temas.netlobby.config

import com.temas.netlobby.auth.AuthService
import com.temas.netlobby.auth.ClientHandler
import com.temas.netlobby.core.*
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
    single { MessageSerializer(get()) }
    single { MessageDecoder(get()) }
    single { MessageEncoder(get()) }
}


val channelModule = module {

    factory {
        val sslCtx: SslContext?
        sslCtx = if (getProperty("enableSSL","false").toBoolean()) {
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            null
        }
        DefaultChannelInitializer(sslCtx, get(), get(), get(named("channelHandlers")), getProperty("maxMessageLength", 500))
    } bind ChannelInitializer::class
}

val clientModule = module {
    single { RequestManager() }

    single(named("channelHandlers")) {
        listOf(ClientHandler(get<RequestManager>()::acceptPong), ClientHandler(get<RequestManager>()::acceptAuth))
    }

    single { TCPClient(get()) }
}

val serverModule = module {
    single(named("channelHandlers")) {
        listOf(AuthService.HandshakeServerHandler, AuthService.LoginHandler)
    }

    single { TCPServer(get()) }
}
