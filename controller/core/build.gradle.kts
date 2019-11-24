val ktorVersion: String by project

plugins {
    id("kotlin")
}

group = "com.staticvillage.ktor"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
}