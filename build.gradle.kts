// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.ClassPaths.gradle)
        classpath(Dependencies.ClassPaths.kotlin)
        classpath(Dependencies.ClassPaths.koin)
        classpath(Dependencies.ClassPaths.navigationSafeArgs)
    }
}


allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
    }
}

tasks.register("clean"){
    println("delete rootProject.buildDir:${rootProject.buildDir}")
    delete(rootProject.buildDir)
}
/*
task clean(type: Delete) {
    delete rootProject.buildDir
}*/