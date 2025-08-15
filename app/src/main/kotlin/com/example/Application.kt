package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.configureSerialization
import com.example.plugins.configureSecurity
import com.example.routes.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureSecurity()     // installs JWT verification
        configureRouting()      // defines /health, /login, /secure/me
    }.start(wait = true)
}
