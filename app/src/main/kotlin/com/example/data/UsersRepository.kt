package com.example.data

import com.example.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UsersRepository {

    fun create(email: String, passwordHash: String, role: String = "member"): User {
        return transaction {
            // Check if email exists
            val exists = UsersTable
                .selectAll()
                .where { UsersTable.email eq email }
                .count() > 0

            if (exists) throw IllegalArgumentException("Email already registered")

            val newId = UUID.randomUUID()

            UsersTable.insert {
                it[id] = newId
                it[UsersTable.email] = email
                it[UsersTable.passwordHash] = passwordHash
                it[UsersTable.role] = role
            }

            User(
                id = newId,
                email = email,
                passwordHash = passwordHash,
                role = role
            )
        }
    }

    fun verify(email: String, passwordHash: String): User? {
        return transaction {
            UsersTable
                .selectAll()
                .where { (UsersTable.email eq email) and (UsersTable.passwordHash eq passwordHash) }
                .map {
                    User(
                        id = it[UsersTable.id],
                        email = it[UsersTable.email],
                        passwordHash = it[UsersTable.passwordHash],
                        role = it[UsersTable.role]
                    )
                }
                .singleOrNull()
        }
    }
}
