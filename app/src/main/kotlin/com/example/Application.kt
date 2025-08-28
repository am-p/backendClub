package com.example

import io.ktor.server.application.*
import com.example.plugins.configureSerialization
import com.example.plugins.configureSecurity
import com.example.routes.configureRouting
import com.example.data.UsersRepository
import com.example.plugins.configureCors
import com.example.data.initDatabase

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureCors()
    initDatabase()
    val users = UsersRepository()
    configureRouting(users)
    
}
