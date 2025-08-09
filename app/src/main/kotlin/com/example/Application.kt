package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) { json() }
        routing {
            get("/health") { call.respond(mapOf("status" to "OK")) }
            post("/login") {
                val body = call.receive<LoginRequest>()
                call.respond(LoginResponse(message = "Welcome ${body.username}"))
            }
        }
    }.start(wait = true)
}

@Serializable data class LoginRequest(val username: String, val password: String)
@Serializable data class LoginResponse(val message: String)
