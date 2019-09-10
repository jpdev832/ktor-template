buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.30")
    }
}

allprojects {
    group = "com.staticvillage.ktor"
    version = "0.0.2"
}

subprojects {
    apply(plugin = "maven")
}

tasks.register("stage") {
    dependsOn(":server:installDist")
}
