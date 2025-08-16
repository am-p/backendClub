package com.example.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable data class RegisterRequest(val email: String, val password: String)
@Serializable data class LoginRequest(val email: String, val password: String)
@Serializable data class LoginResponse(val message: String, val token: String)

data class User(
    val id: UUID,
    val email: String,
    val passwordHash: String,
    val role: String = "member"
)
