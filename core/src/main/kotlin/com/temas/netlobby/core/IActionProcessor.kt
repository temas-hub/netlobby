package com.temas.netlobby.core

import com.temas.netlobby.core.status.Action

interface IActionProcessor {
    fun process(actions: List<Action>)
}