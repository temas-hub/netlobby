package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

interface ServerContext {
    fun actionProcessed(action: Action)
}