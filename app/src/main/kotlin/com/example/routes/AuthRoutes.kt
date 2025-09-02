package com.example.routes

import com.example.data.UsersRepository
import com.example.models.LoginRequest
import com.example.models.LoginResponse
import com.example.models.RegisterRequest
import com.example.plugins.JwtService
import io.ktor.http.*
import io.ktor.server.application.Application
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(users: UsersRepository) {
    routing {
        get("/health") { call.respond(mapOf("status" to "OK")) }

        post("/auth/register") {
            val body = call.receive<RegisterRequest>()
            try {
                val newUser = users.create(body.email, body.password)
                val token = JwtService.generateToken(subject = newUser.email, role = newUser.role)
                call.respond(HttpStatusCode.Created, LoginResponse(message = "Welcome ${newUser.email}", token = token))
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to e.message))
            }
        }

        post("/auth/login") {
            val body = call.receive<LoginRequest>()
            val user = users.verify(body.email, body.password)
            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
                return@post
            }
            val token = JwtService.generateToken(subject = user.email, role = user.role)
            call.respond(LoginResponse(message = "Welcome ${user.email}", token = token))
        }

        authenticate("auth-jwt") {
            get("/auth/validate") {
                val p = call.principal<JWTPrincipal>()!!
                call.respond(
                    mapOf(
                        ("user" to mapOf("email" to p.subject))
                    )
                )
            }
        }
    }
}
