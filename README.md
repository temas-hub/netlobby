Netlobby is a simple library for client server multiplayer games. 

## Principles
* Server is the source of truth. It maintains game state and sends it to clients
* Clients send player actions to server
* Game developer implements both server and client part
  * Server
    * implement action handlers and update game state accordingly
    * setup game state update period in ms
  * Client
    * sending action to server
    * game state updates handler

## Features
* FST serialization and UDP transport for low latency
* Easy to use API
* Kotlin multiplatform to support mobile and web clients 
* TODO: authentication
* TODO: lobby management

## Usage

### Server
```kotlin
import com.temas.netlobby.core.model.*
import com.temas.netlobby.core.bootstrap.ServerBuilder

val server = ServerBuilder()
    .withActionHandler {
        model++
        println(it)
        it.id
    }
    .withUpdateBuilder { DummyModel() }
    .updatePeriod(300)
    .build()

server.start()
```

### Client
```kotlin
import com.temas.netlobby.core.model.*
import com.temas.netlobby.core.bootstrap.ClientBuilder

val client = ClientBuilder()
    .withInboundHandler { 
        println(it) 
    }
    .build()

val connection = client.connect()
connection.sendActions(listOf(Action(DummyActionType())))
```



