package com.example.data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable : Table("users") {
    val id = uuid("id").uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = varchar("role", 50).default("member")

    override val primaryKey = PrimaryKey(id)
}

fun initDatabase() {
    Database.connect("jdbc:sqlite:backendClub.db", driver = "org.sqlite.JDBC")

    transaction {
        SchemaUtils.create(UsersTable)
    }
}
