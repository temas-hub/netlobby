package com.temas.netlobby.core

import com.temas.netlobby.config.channelModule
import com.temas.netlobby.config.clientModule
import com.temas.netlobby.config.serializationModule
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

class TestLogin : KoinTest {
    companion object {
        val SERVER_HOST = "localhost"
        val SERVER_PORT = 17999
        val EMAIL = "email@domain.com"
    }

    val testModule = module {
        single { ClientComponent() }
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        environmentProperties()
        modules(serializationModule, channelModule, clientModule, testModule)
    }

//    @Test
//    fun `ping server successfully` () {
//        val client: ClientComponent by inject()
//        val pingProcess = client.ping()
//        val result = pingProcess.get()
//        assertNotNull(result)
//    }

    @Test
    fun `login to server successfully`() {
        val client: ClientComponent by inject()
        val loginProcess = client.login(SERVER_HOST, SERVER_PORT, EMAIL)
        val session = loginProcess.get()
        assertNotNull(session)
        assertNotNull(session.sessionId)

        //wait for lobby
//        val lobbyResult = session.joinLobby().waitFor()
//        assertNotNull(lobbyResult.getLobbyId())
    }

}