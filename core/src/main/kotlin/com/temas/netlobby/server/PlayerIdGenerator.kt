package com.temas.netlobby.server

object PlayerIdGenerator {
    var id: Int = 0
        get() = ++field
}