package com.temas.netlobby.core

import com.temas.netlobby.config.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class TestUDP: KoinTest {

    val testModule = module {

    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        environmentProperties()
        modules(serializationModule, channelModule, clientModule, testModule)
    }

    @Test
    fun `send udp status to server successfully`() {
//        val client: UdpClientBootstrap by inject()
//        val server: UdpServerBootstrap by inject()
//        val sessionRegistry: SessionRegistry by inject()

//        server.start().await()
//        client.start().await()
//        assertEquals(1, sessionRegistry.keys.size)
    }

}