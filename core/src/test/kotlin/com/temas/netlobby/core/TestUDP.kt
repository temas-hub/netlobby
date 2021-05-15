package com.temas.netlobby.core

import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest

class TestUDP: KoinTest {

    val testModule = module {

    }

//    @get:Rule
//    val koinTesClientRule = KoinTestRule.create {
//        printLogger()
//        environmentProperties()
//        modules(serializationModule, channelModule, clientModule, testModule)
//    }
//
//    @get:Rule
//    val koinTesClientRule = KoinTestRule.create {
//        printLogger()
//        environmentProperties()
//        modules(serializationModule, channelModule, clientModule, testModule)
//    }

    @Test
    fun `send udp status to server successfully`() {
//        var actualTimestamp: Long? = null
//        val udpClient = UdpClientWithServerBuilder()
//            .clientInboundHandlers(arrayOf({ m -> actualTimestamp = (m as StatusMessage).status.serverTimestamp}))
//            .build()
//
//        val server = UdpServerBuilder()
//            .inboundHandlers(arrayOf( { msg ->  println(msg) }))
//            .build()
//        val serverChannelFuture = server.init()
//
//        val channel = udpClient.connect().channel()
//
//        val statusMessage = TransferableMessage(null, StatusMessage(Status(999)))
//        channel.writeAndFlush(statusMessage)
//
//        assertEquals(999, actualTimestamp)
//        val client: UdpClientBootstrap by inject()
//        val server: UdpServerBootstrap by inject()
//        val sessionRegistry: SessionRegistry by inject()

//        server.start().await()
//        client.start().await()
//        assertEquals(1, sessionRegistry.keys.size)
    }

}