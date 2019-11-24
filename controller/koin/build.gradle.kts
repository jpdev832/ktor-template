val ktorVersion: String by project

plugins {
    id("kotlin")
}

group = "com.staticvillage.ktor"
version = "0.0.2"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testCompile("junit", "junit", "4.12")

    implementation(project(":controller:core"))
    implementation("org.koin:koin-ktor:2.0.0")
}