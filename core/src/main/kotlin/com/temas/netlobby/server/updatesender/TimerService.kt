package com.temas.netlobby.server.updatesender

/**
 * Creates a scheduled task to send updates to clients.
 */
interface TimerService {

    /**
     * Starts sending updates to clients.
     * @param updatePeriodMs period between updates in milliseconds. If 0, no updates will be sent.
     */
    fun start(updatePeriodMs: Long)
    /**
     * Stops sending updates to clients. Does nothing if updates are not started.
     */
    fun stop()
}