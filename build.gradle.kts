buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.30")
    }
}

group = "ktor-template"
version = "0.0.1"

tasks.register("stage") {
    dependsOn(":server:installDist")
}
