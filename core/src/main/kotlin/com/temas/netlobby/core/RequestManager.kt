package com.temas.netlobby.core

import java.lang.IllegalStateException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicLong

class RequestManager {
    val pongCounter: AtomicLong = AtomicLong(0)
    val pongAcceptor = CompletableFuture<Pong>()
    val loginResponseAcceptor = CompletableFuture<AuthResponse>()

    fun pongAcceptor(): CompletableFuture<Pong> {
        return pongAcceptor;
    }

    fun acceptPong(message: Pong) {
        pongCounter.incrementAndGet()
        pongAcceptor.complete(message)
    }

    fun acceptAuth(message: SuccessAuthResponse) {
        loginResponseAcceptor.complete(message)
        println("Authorized")
    }

    fun loginResponse() : CompletableFuture<Session>{
        return loginResponseAcceptor
                .thenApply { r ->
            when(r) {
                is SuccessAuthResponse -> Session(r.sessionId)
                is FailedAuthResponse -> {
                    println(r.error)
                    throw IllegalStateException("Failed auth response: ${r.error}")
                }
            }
        }
    }
}