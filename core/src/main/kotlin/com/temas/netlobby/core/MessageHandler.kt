package com.temas.netlobby.core

interface MessageHandler<in T: Message> {
    fun handle(message: T)
}