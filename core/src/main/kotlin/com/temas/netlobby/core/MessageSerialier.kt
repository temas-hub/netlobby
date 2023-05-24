package com.temas.netlobby.core

import org.nustaq.serialization.FSTConfiguration

/**
 * Massage serializer based on FST
 */
class MessageSerializer(private val fstConfiguration: FSTConfiguration) {
    fun decode(bytes: ByteArray) =
         fstConfiguration.asObject(bytes)

    fun encode(message: Message) =
            fstConfiguration.asByteArray(message)
}