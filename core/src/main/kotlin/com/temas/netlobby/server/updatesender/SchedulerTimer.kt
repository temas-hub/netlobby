package com.temas.netlobby.server.updatesender

import java.util.*
import kotlin.concurrent.schedule

/**
 * Default implementation timer service which schedule a task using java Timer
 * @see java.util.Timer
 */
class SchedulerTimer(
    private val timer: Timer,
    private val updateSender: UpdateSender
): TimerService {
    var timerTask: TimerTask? = null

    override fun start(updatePeriodMs: Long) {
        if (updatePeriodMs > 0) {
            timerTask = timer.schedule(0, updatePeriodMs) {
                updateSender.sendUpdates()
            }
        }
    }

    override fun stop() {
        timerTask?.cancel()
    }
}