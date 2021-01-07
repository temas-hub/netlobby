package com.temas.netlobby.client

import com.temas.netlobby.config.clientModule
import com.temas.netlobby.core.StatusMessage
import com.temas.netlobby.core.TransferableMessage
import com.temas.netlobby.core.UdpComponentBuilder
import com.temas.netlobby.core.status.Status
import java.net.InetSocketAddress
import java.util.*
import kotlin.concurrent.schedule

class UdpClientBuilder: UdpComponentBuilder<UDPClient>() {
    override fun build(): UDPClient = buildKoin(clientModule).get()
}

fun main(args: Array<String>) {
    var latestServerTimestamp: Long? = null
    val udpClient = UdpClientBuilder()
        .inboundHandlers(arrayOf({ m -> latestServerTimestamp = (m as StatusMessage).status.serverTimestamp}))
        .build()
        val channel = udpClient.connect().channel()

    val address = InetSocketAddress( //TODO get address from config
        "localhost", 17999
    )

    Timer("Update sender", false).schedule(0,2000) {
        val statusMessage = TransferableMessage(address, StatusMessage(Status(latestServerTimestamp)))
        channel.writeAndFlush(statusMessage)
    }
}