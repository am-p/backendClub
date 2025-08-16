package com.example.data

import java.util.Locale    
import com.example.models.User
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import at.favre.lib.crypto.bcrypt.BCrypt

class UsersRepository {
    private val usersByEmail = ConcurrentHashMap<String, User>() //<--acá storeo momentaneamente, hasta que use SQLite

    fun normalize(email: String) = email.trim().lowercase(Locale.ROOT)

    fun create(emailRaw: String, password: String, role: String = "member"): User {
        val email = normalize(emailRaw)
        require(!usersByEmail.containsKey(email)) { "Email already registered" }
        val hash = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        val user = User(id = UUID.randomUUID(), email = email, passwordHash = hash, role = role)
        usersByEmail[email] = user
        return user
    }

    fun findByEmail(emailRaw: String): User? = usersByEmail[normalize(emailRaw)]

    fun verify(emailRaw: String, password: String): User? {
        val user = findByEmail(emailRaw) ?: return null
        val res = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
        return if (res.verified) user else null
    }
}
