package com.temas.netlobby.core

import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicLong

class ResponseAcceptor {
    val pongCounter: AtomicLong = AtomicLong(0)
    val pongAcceptor = CompletableFuture<Pong>()

    fun pongAcceptor(): CompletableFuture<Pong> {
        return pongAcceptor;
    }

    fun acceptPong(message: Pong) {
        pongCounter.incrementAndGet()
        pongAcceptor.complete(message)
    }

    fun acceptAuth(message: SuccessAuthResponse) {
        println("Authorized")
    }
}