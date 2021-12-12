// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version = "1.3.72"
    val koin_version = "2.2.0-rc-2"

    val nav_version = "2.3.0"
    val kapt_version = "1.4.10"

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.koin:koin-gradle-plugin:$koin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}


allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://maven.google.com")
    }
}
/*
task.register("clean"){
    println("delete rootProject.buildDir")
    delete(rootProject.buildDir)
}
 */
/*
task clean(type: Delete) {
    delete rootProject.buildDir
}*/