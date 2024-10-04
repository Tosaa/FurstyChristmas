// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins{
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    kotlin("plugin.serialization") version "2.0.20" apply false
}
buildscript {
    repositories {
        mavenCentral()
        google()
    }
}

repositories {
    mavenCentral()
    google()
}

tasks.register("clean") {
    println("delete rootProject.buildDir:${rootProject.layout.buildDirectory}")
    delete(rootProject.layout.buildDirectory)
}
/*
task clean(type: Delete) {
    delete rootProject.buildDir
}*/
