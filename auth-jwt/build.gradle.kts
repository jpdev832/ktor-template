val ktorVersion: String by project

plugins {
    id("kotlin")
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

    implementation(project(":error"))

    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    // Functional
    implementation("io.arrow-kt:arrow-core:0.8.2")

    implementation("joda-time:joda-time:2.10.3")
}