package com.temas.netlobby.server

import com.temas.netlobby.core.status.Action

interface IActionProcessor {
    fun process(actions: List<Action>)
}