package com.temas.netlobby.server

import com.temas.netlobby.config.serverModule
import com.temas.netlobby.core.ActionMessage
import com.temas.netlobby.core.UdpComponentBuilder

class UdpServerBuilder: UdpComponentBuilder<UDPServer, ActionMessage>() {
    val koin = buildKoin(serverModule)
    override fun build(): UDPServer {
        koin.get<UpdateSender>()
        return koin.get()
    }
}

fun main(args: Array<String>) {
    val bootstrap = UdpServerBuilder()
        .inboundHandlers(listOf { msg -> println(msg) })
        .build()
    bootstrap.init().get()
}