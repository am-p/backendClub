package com.example

import io.ktor.server.application.*
import com.example.plugins.configureSerialization
import com.example.plugins.configureSecurity
import com.example.routes.configureRouting
import com.example.data.UsersRepository

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSerialization()
    configureSecurity()
    val users = UsersRepository()
    configureRouting(users)


}
