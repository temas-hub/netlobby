package com.temas.netlobby.server.generator

/**
 * Generates unique player ids.
 */
object PlayerIdGenerator {
    var id: Int = 0
        get() = ++field
}