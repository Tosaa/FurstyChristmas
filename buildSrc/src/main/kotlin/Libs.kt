object Libs {
    object Name {
        // Integration with activities
        const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose"

        // Compose Material Design
        const val COMPOSE_MATERIAL = "androidx.compose.material:material"

        // Animations
        const val COMPOSE_ANIMATION = "androidx.compose.animation:animation"

        // Tooling support (Previews, etc.)
        const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling"

        // Integration with ViewModels
        const val COMPOSE_VIEWMODEL_LIFECYCLE = "androidx.lifecycle:lifecycle-viewmodel-compose"

        // UI Tests
        const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout"
        const val APP_COMPAT = "androidx.appcompat:appcompat"
        const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4"
        const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions"
        const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx"
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib"
        const val CORE_KTX = "androidx.core:core-ktx"
        const val JUNIT = "junit:junit"
        const val JUNIT_EXT = "androidx.test.ext:junit"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core"
        const val RECYCLERVIEW = "androidx.recyclerview:recyclerview"
        const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui"
        const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx"
        const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx"
        const val NAVIGATION_DYNAMICS_FEATURES_FRAGMENT = "androidx.navigation:navigation-dynamic-features-fragment"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx"

        // Koin Core features
        const val KOIN_CORE = "io.insert-koin:koin-core"

        // Koin Test features
        const val KOIN_TEST = "io.insert-koin:koin-test"

        // Koin main features for Android
        const val KOIN_ANDROID = "io.insert-koin:koin-android"
// No more koin-android-viewmodel, koin-android-scope, koin-android-fragment

        // Java Compatibility
        const val KOIN_ANDROID_COMPAT = "io.insert-koin:koin-android-compat"

        // Jetpack WorkManager
        const val KOIN_WORKMANAGER = "io.insert-koin:koin-androidx-workmanager"

        // Navigation Graph
        const val KOIN_NAVIGATION = "io.insert-koin:koin-androidx-navigation"

        // Jetpack Compose
        const val KOIN_COMPOSE = "io.insert-koin:koin-androidx-compose"


        const val GOOGLE_MATERIAL = "com.google.android.material:material"
        const val ROOM_RUNTIME = "androidx.room:room-runtime"
        const val ROOM_KTX = "androidx.room:room-ktx"
        const val ROOM_COMPILER = "androidx.room:room-compiler"
        const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx"
        const val MOSHI_KOTLIN_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin"
        const val MOSHI = "com.squareup.moshi:moshi"
        const val TIMBER = "com.jakewharton.timber:timber"
    }

    object Version {
        private const val nav_version = "2.3.0"
        private const val kotlin_version = "1.7.10"
        private const val koin_version = "3.2.0"
        private const val room_version = "2.4.3"
        private const val moshi_version = "1.11.0"
        private const val lifecycle_version = "2.2.0"
        const val COMPOSE_COMPILER = "1.3.0"
        const val COMPOSE_ACTIVITY = "1.5.1"
        const val COMPOSE_MATERIAL = "1.2.1"
        const val COMPOSE_ANIMATION = "1.2.1"
        const val COMPOSE_TOOLING = "1.2.1"
        const val COMPOSE_VIEWMODEL_LIFECYCLE = "2.5.1"
        const val COMPOSE_JUNIT = "1.2.1"
        const val CONSTRAINT_LAYOUT = "2.0.1"
        const val APP_COMPAT = "1.2.0"
        const val LEGACY_SUPPORT = "1.0.0"
        const val LIFECYCLE_EXTENSIONS = lifecycle_version
        const val LIFECYCLE_VIEWMODEL = lifecycle_version
        const val KOTLIN_STDLIB = kotlin_version
        const val CORE_KTX = "1.3.2"
        const val JUNIT = "4.13"
        const val JUNIT_EXT = "1.1.2"
        const val ESPRESSO_CORE = "3.3.0"
        const val RECYCLERVIEW = "1.1.0"
        const val NAVIGATION_FRAGMENT = nav_version
        const val NAVIGATION_UI = nav_version
        const val NAVIGATION_FRAGMENT_KTX = nav_version
        const val NAVIGATION_UI_KTX = nav_version
        const val NAVIGATION_DYNAMICS_FEATURES_FRAGMENT = nav_version
        const val FRAGMENT_KTX = "1.2.5"
        const val KOIN_CORE = koin_version
        const val KOIN_ANDROIDX_SCOPE = koin_version
        const val KOIN_ANDROIDX_VIEWMODEL = koin_version
        const val KOIN_ANDROIDX_FRAGMENT = koin_version
        const val KOIN_ANDROIDX_WORKMANAGER = koin_version
        const val GOOGLE_MATERIAL = "1.2.1"
        const val ROOM_RUNTIME = room_version
        const val ROOM_KTX = room_version
        const val ROOM_COMPILER = room_version
        const val LIFECYCLE_LIVEDATA = "2.2.0"
        const val MOSHI_KOTLIN_CODEGEN = "$moshi_version" //"1.10
        const val MOSHI_KOTLIN = moshi_version
        const val MOSHI = moshi_version
        const val TIMBER = "4.7.1"
    }
}