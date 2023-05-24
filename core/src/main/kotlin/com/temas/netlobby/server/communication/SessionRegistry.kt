package com.temas.netlobby.server.communication


/**
 * Registry for communication sessions. Session is a communication channel between server and client
 * Key is used to identify the session
 */
interface SessionRegistry<KEY, COMMUNICATION> {

    /**
     * Register a new session to the manager. Address is used as a key for the session
     * @param key key for the session
     * @param communication communication channel for the session
     */
    fun addSession(key: KEY, communication: COMMUNICATION): CommunicationSession

    /**
     * List all registered sessions
     */
    fun listSessions(): List<CommunicationSession>

    /**
     * Remove session from the registry
     * @param key key of the session to remove
     */
    fun removeSession(key: KEY)
}