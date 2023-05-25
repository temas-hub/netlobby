package com.temas.netlobby.core

import com.temas.netlobby.core.model.Message
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