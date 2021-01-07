package com.temas.netlobby.server

import com.temas.netlobby.config.serverModule
import com.temas.netlobby.core.UdpComponentBuilder

class UdpServerBuilder: UdpComponentBuilder<UDPServer>() {
    val koin = buildKoin(serverModule)
    override fun build(): UDPServer {
        koin.get<UpdateSender>()
        return koin.get<UDPServer>()
    }
}

fun main(args: Array<String>) {
    val bootstrap = UdpServerBuilder()
        .inboundHandlers(arrayOf( { msg ->  println(msg) }))
        .build()
    bootstrap.init().get()
}