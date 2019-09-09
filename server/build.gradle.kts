val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project

plugins {
    id("kotlin")
    id("application")
}

group = "ktor-template"
version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/ktor")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    // Internal
    implementation(project(":auth-jwt"))
    implementation(project(":error"))
    implementation(project(":store"))
    implementation(project(":store-postgres"))

    // Ktor
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.jetbrains.exposed:exposed:0.13.2")
    implementation("com.zaxxer:HikariCP:3.3.1")

    // Dependency Injection
    implementation("org.koin:koin-ktor:2.0.0")

    // Functional
    implementation("io.arrow-kt:arrow-core:0.8.2")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

