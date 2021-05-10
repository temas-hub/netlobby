package com.temas.netlobby.client

import com.temas.netlobby.config.clientModule
import com.temas.netlobby.core.StateMessage
import com.temas.netlobby.core.TransferableMessage
import com.temas.netlobby.core.UdpComponentBuilder
import java.util.*
import kotlin.concurrent.schedule

class UdpClientBuilder: UdpComponentBuilder<UDPClient, StateMessage>() {
    override fun build(): UDPClient = buildKoin(clientModule).get()
}

fun main(args: Array<String>) {
//    var latestServerTimestamp: Long? = null
//    val udpClient = UdpClientBuilder()
//        .inboundHandlers { latestServerTimestamp = it}
//        .build()
//    val channel = udpClient.connect().channel()
//
//    Timer("Update sender", false).schedule(0,2000) {
//        val statusMessage = TransferableMessage(null, StatusMessage(Status(latestServerTimestamp)))
//        channel.writeAndFlush(statusMessage)
//    }
}