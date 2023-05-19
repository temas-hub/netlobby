package com.temas.netlobby.server

/**
 * Created by azhdanov on 19.05.2023.
 */
interface TimerService {
    fun start(updatePeriodMs: Long)
    fun stop()
}