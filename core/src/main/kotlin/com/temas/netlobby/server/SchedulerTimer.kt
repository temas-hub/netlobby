package com.temas.netlobby.server

import java.util.*
import kotlin.concurrent.schedule


/**
 * Created by azhdanov on 19.05.2023.
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