plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("koin")
    id("androidx.navigation.safeargs")
}
android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"
    // archivesBaseName("furstychristmas")
    defaultConfig {
        applicationId = "redtoss.example.furstychristmas"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 2
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile ("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}


dependencies {
    implementation("${Libs.LEGACY_SUPPORT}:${Libs.LEGACY_SUPPORT_VERSION}")
    implementation("${Libs.LIFECYCLE_EXTENSIONS}:${Libs.LIFECYCLE_EXTENSIONS_VERSION}")
    implementation("${Libs.LIFECYCLE_VIEWMODEL}:${Libs.LIFECYCLE_VIEWMODEL_VERSION}")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("${Libs.KOTLIN_STDLIB}:${Libs.KOTLIN_STDLIB_VERSION}")
    implementation("${Libs.CORE_KTX}:${Libs.CORE_KTX_VERSION}")
    implementation("${Libs.APP_COMPAT}:${Libs.APP_COMPAT_VERSION}")
    implementation("${Libs.CONSTRAINT_LAYOUT}:${Libs.CONSTRAINT_LAYOUT_VERSION}")
    testImplementation("${Libs.JUNIT}:${Libs.JUNIT_VERSION}")
    androidTestImplementation("${Libs.JUNIT_EXT}:${Libs.JUNIT_EXT_VERSION}")
    androidTestImplementation("${Libs.ESPRESSO_CORE}:${Libs.ESPRESSO_CORE_VERSION}")
    implementation("${Libs.RECYCLERVIEW}:${Libs.RECYCLERVIEW_VERSION}")

    //Navigation
    implementation("${Libs.NAVIGATION_FRAGMENT}:${Libs.NAVIGATION_FRAGMENT_VERSION}")
    implementation("${Libs.NAVIGATION_FRAGMENT_KTX}:${Libs.NAVIGATION_FRAGMENT_KTX_VERSION}")
    implementation("${Libs.NAVIGATION_UI}:${Libs.NAVIGATION_UI_VERSION}")
    implementation("${Libs.NAVIGATION_UI_KTX}:${Libs.NAVIGATION_UI_KTX_VERSION}")
    implementation("${Libs.NAVIGATION_DYNAMICS_FEATURES_FRAGMENT}:${Libs.NAVIGATION_DYNAMICS_FEATURES_FRAGMENT_VERSION}")

    implementation("${Libs.FRAGMENT_KTX}:${Libs.FRAGMENT_KTX_VERSION}")
    // Koin DI
    implementation("${Libs.KOIN_CORE}:${Libs.KOIN_CORE_VERSION}")
    implementation("${Libs.KOIN_ANDROIDX_SCOPE}:${Libs.KOIN_ANDROIDX_SCOPE_VERSION}")
    implementation("${Libs.KOIN_ANDROIDX_VIEWMODEL}:${Libs.KOIN_ANDROIDX_VIEWMODEL_VERSION}")
    implementation("${Libs.KOIN_ANDROIDX_FRAGMENT}:${Libs.KOIN_ANDROIDX_FRAGMENT_VERSION}")
    implementation("${Libs.KOIN_ANDROIDX_WORKMANAGER}:${Libs.KOIN_ANDROIDX_WORKMANAGER_VERSION}")

    implementation("${Libs.GOOGLE_MATERIAL}:${Libs.GOOGLE_MATERIAL_VERSION}")
    // Room DB
    implementation("${Libs.ROOM_RUNTIME}:${Libs.ROOM_RUNTIME_VERSION}")
    implementation("${Libs.ROOM_KTX}:${Libs.ROOM_KTX_VERSION}")
    kapt("${Libs.ROOM_COMPILER}:${Libs.ROOM_COMPILER_VERSION}")

    implementation("${Libs.LIFECYCLE_LIVEDATA}:${Libs.LIFECYCLE_LIVEDATA_VERSION}")
    // moshi for json reading
    implementation("${Libs.MOSHI}:${Libs.MOSHI_VERSION}")
    implementation("${Libs.MOSHI_KOTLIN}:${Libs.MOSHI_KOTLIN_VERSION}")
    kapt("${Libs.MOSHI_KOTLIN_CODEGEN}:${Libs.MOSHI_KOTLIN_CODEGEN_VERSION}")
    //Timber
    implementation("${Libs.TIMBER}:${Libs.TIMBER_VERSION}")
}