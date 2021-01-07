package com.temas.netlobby.core.status

import java.io.Serializable

data class Action(val type: String) : Serializable

data class Status(val serverTimestamp: Long?) : Serializable