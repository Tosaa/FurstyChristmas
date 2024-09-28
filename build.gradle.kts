// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins{
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.25" apply false
}
buildscript {
    repositories {
        google()
    }
}

tasks.register("clean") {
    println("delete rootProject.buildDir:${rootProject.layout.buildDirectory}")
    delete(rootProject.layout.buildDirectory)
}
/*
task clean(type: Delete) {
    delete rootProject.buildDir
}*/
