package com.temas.netlobby.server.generator

/**
 * Created by azhdanov on 24.05.2023.
 */
object LocalSessionIdGenerator {
    var id: Int = 0
        get() = ++field
}