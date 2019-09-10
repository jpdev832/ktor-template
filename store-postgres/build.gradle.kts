val ktorVersion: String by project

plugins {
    id("kotlin")
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/ktor")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":store"))

    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.jetbrains.exposed:exposed:0.13.2")
    implementation("com.zaxxer:HikariCP:3.3.1")
}