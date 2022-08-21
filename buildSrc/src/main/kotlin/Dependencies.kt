object PluginVersions{
    const val test = "0.0.1"
}

object Dependencies{
    object Versions{
        const val gradle = "7.2.2"
        const val kotlin = "1.7.10"
        const val koin = "3.2.0"
        const val navigationSafeArgs = "2.3.0"
    }
    object ClassPaths{
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val koin = "org.koin:koin-gradle-plugin:${Versions.koin}"
        const val navigationSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSafeArgs}"
    }
}