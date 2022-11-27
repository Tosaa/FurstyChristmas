object Dependencies {
    object Versions {
        const val gradle = "7.3.1"
        const val kotlin = "1.7.10"
        const val koin = "3.2.0"
        const val navigationSafeArgs = "2.3.0"
    }

    object ClassPaths {
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val koin = "org.koin:koin-gradle-plugin:${Versions.koin}"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgs}"
    }
}
