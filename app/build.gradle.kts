plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    application
}

repositories { mavenCentral() }

dependencies {
    val ktorVersion = "2.3.6"
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.example.ApplicationKt")
}

kotlin { jvmToolchain(21) }
