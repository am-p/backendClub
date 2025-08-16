package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

data class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
    val accessTtlSec: Long
)

object JwtService {
    private lateinit var cfg: JwtConfig

    fun init(cfg: JwtConfig) { this.cfg = cfg }

    fun generateToken(subject: String, role: String = "member"): String {
        val now = System.currentTimeMillis()
        return JWT.create()
            .withIssuer(cfg.issuer)
            .withAudience(cfg.audience)
            .withIssuedAt(java.util.Date(now))
            .withExpiresAt(java.util.Date(now + cfg.accessTtlSec * 1000))
            .withSubject(subject)                
            .withClaim("role", role)
            .sign(Algorithm.HMAC256(cfg.secret))
    }
}

fun Application.configureSecurity() {
    val c = environment.config
    val cfg = JwtConfig(
        secret       = c.property("jwt.secret").getString(),
        issuer       = c.property("jwt.issuer").getString(),
        audience     = c.property("jwt.audience").getString(),
        realm        = c.property("jwt.realm").getString(),
        accessTtlSec = c.property("jwt.accessTtlSec").getString().toLong()
    )
    JwtService.init(cfg)

    install(Authentication) {
        jwt("auth-jwt") {
            realm = cfg.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(cfg.secret))
                    .withIssuer(cfg.issuer)
                    .withAudience(cfg.audience)
                    .build()
            )
            validate { cred ->
                val sub = cred.payload.subject
                if (!sub.isNullOrBlank()) JWTPrincipal(cred.payload) else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Invalid or expired token")
            }
        }
    }
}
