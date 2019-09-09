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

    implementation("io.ktor:ktor-server-core:$ktorVersion")
}