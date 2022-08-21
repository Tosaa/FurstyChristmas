plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    //kotlin("android.extensions")

    id("kotlin-kapt")
    // id("koin")
    id("androidx.navigation.safeargs")
}
android {
    compileSdkVersion(33)
    buildToolsVersion = "30.0.2"
    // archivesBaseName("furstychristmas")
    defaultConfig {
        applicationId = "redtoss.example.furstychristmas"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 2
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Version.COMPOSE_COMPILER
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {
    implementation("${Libs.Name.LEGACY_SUPPORT}:${Libs.Version.LEGACY_SUPPORT}")
    implementation("${Libs.Name.LIFECYCLE_EXTENSIONS}:${Libs.Version.LIFECYCLE_EXTENSIONS}")
    implementation("${Libs.Name.LIFECYCLE_VIEWMODEL}:${Libs.Version.LIFECYCLE_VIEWMODEL}")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("${Libs.Name.KOTLIN_STDLIB}:${Libs.Version.KOTLIN_STDLIB}")
    implementation("${Libs.Name.CORE_KTX}:${Libs.Version.CORE_KTX}")
    implementation("${Libs.Name.APP_COMPAT}:${Libs.Version.APP_COMPAT}")
    implementation("${Libs.Name.CONSTRAINT_LAYOUT}:${Libs.Version.CONSTRAINT_LAYOUT}")
    // implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    // implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    // implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    testImplementation("${Libs.Name.JUNIT}:${Libs.Version.JUNIT}")
    androidTestImplementation("${Libs.Name.JUNIT_EXT}:${Libs.Version.JUNIT_EXT}")
    androidTestImplementation("${Libs.Name.ESPRESSO_CORE}:${Libs.Version.ESPRESSO_CORE}")
    implementation("${Libs.Name.RECYCLERVIEW}:${Libs.Version.RECYCLERVIEW}")

    //Navigation
    implementation("${Libs.Name.NAVIGATION_FRAGMENT}:${Libs.Version.NAVIGATION_FRAGMENT}")
    implementation("${Libs.Name.NAVIGATION_FRAGMENT_KTX}:${Libs.Version.NAVIGATION_FRAGMENT_KTX}")
    implementation("${Libs.Name.NAVIGATION_UI}:${Libs.Version.NAVIGATION_UI}")
    implementation("${Libs.Name.NAVIGATION_UI_KTX}:${Libs.Version.NAVIGATION_UI_KTX}")
    implementation("${Libs.Name.NAVIGATION_DYNAMICS_FEATURES_FRAGMENT}:${Libs.Version.NAVIGATION_DYNAMICS_FEATURES_FRAGMENT}")

    implementation("${Libs.Name.FRAGMENT_KTX}:${Libs.Version.FRAGMENT_KTX}")
    // Koin DI
    implementation("${Libs.Name.KOIN_CORE}:${Libs.Version.KOIN_CORE}")
    implementation("${Libs.Name.KOIN_ANDROID}:${Libs.Version.KOIN_CORE}")
    implementation("${Libs.Name.KOIN_COMPOSE}:${Libs.Version.KOIN_CORE}")
    implementation("${Libs.Name.KOIN_ANDROID_COMPAT}:${Libs.Version.KOIN_CORE}")
    implementation("${Libs.Name.KOIN_NAVIGATION}:${Libs.Version.KOIN_CORE}")
    implementation("${Libs.Name.KOIN_WORKMANAGER}:${Libs.Version.KOIN_CORE}")
    testImplementation("${Libs.Name.KOIN_TEST}:${Libs.Version.KOIN_CORE}")

    implementation("${Libs.Name.GOOGLE_MATERIAL}:${Libs.Version.GOOGLE_MATERIAL}")
    // Room DB
    implementation("${Libs.Name.ROOM_RUNTIME}:${Libs.Version.ROOM_RUNTIME}")
    implementation("${Libs.Name.ROOM_KTX}:${Libs.Version.ROOM_KTX}")
    // debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_version"]}")
    kapt("${Libs.Name.ROOM_COMPILER}:${Libs.Version.ROOM_COMPILER}")

    implementation("${Libs.Name.LIFECYCLE_LIVEDATA}:${Libs.Version.LIFECYCLE_LIVEDATA}")
    // moshi for json reading
    implementation("${Libs.Name.MOSHI}:${Libs.Version.MOSHI}")
    implementation("${Libs.Name.MOSHI_KOTLIN}:${Libs.Version.MOSHI_KOTLIN}")
    kapt("${Libs.Name.MOSHI_KOTLIN_CODEGEN}:${Libs.Version.MOSHI_KOTLIN_CODEGEN}")
    //Timber
    implementation("${Libs.Name.TIMBER}:${Libs.Version.TIMBER}")
    // Compose UI
    implementation("${Libs.Name.COMPOSE_ACTIVITY}:${Libs.Version.COMPOSE_ACTIVITY}")
    implementation("${Libs.Name.COMPOSE_MATERIAL}:${Libs.Version.COMPOSE_MATERIAL}")
    implementation("${Libs.Name.COMPOSE_ANIMATION}:${Libs.Version.COMPOSE_ANIMATION}")
    implementation("${Libs.Name.COMPOSE_TOOLING}:${Libs.Version.COMPOSE_TOOLING}")
    implementation("${Libs.Name.COMPOSE_VIEWMODEL_LIFECYCLE}:${Libs.Version.COMPOSE_VIEWMODEL_LIFECYCLE}")
    testImplementation("${Libs.Name.COMPOSE_JUNIT}:${Libs.Version.COMPOSE_JUNIT}")
}