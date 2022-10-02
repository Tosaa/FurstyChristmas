import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

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
    // archivesBaseName("furstychristmas")
    defaultConfig {
        applicationId = "redtoss.example.furstychristmas"
        minSdkVersion(26)
        targetSdkVersion(33)
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
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies{
    // COMPOSE
    implementation("androidx.activity","activity-compose", "1.5.1")
    implementation("androidx.compose.material","material", "1.2.1")
    implementation("androidx.compose.animation","animation","1.2.1")
    implementation("androidx.compose.ui","ui-tooling","1.2.1")
    implementation("androidx.compose.ui","ui-tooling-preview","1.2.1")
    debugImplementation("androidx.compose.ui","ui-test-manifest","1.2.1")
    implementation("androidx.lifecycle","lifecycle-viewmodel-compose","2.5.1")
    testImplementation("androidx.compose.ui","ui-test-junit4","1.2.1")

    // LIFECYCLE
    implementation("androidx.lifecycle","lifecycle-extensions","2.2.0") // LIFECYCLE_EXTENSIONS
    implementation("androidx.lifecycle","lifecycle-viewmodel-ktx","2.2.0") // LIFECYCLE_VIEWMODEL
    implementation("androidx.lifecycle","lifecycle-livedata-ktx","2.2.0") // LIFECYCLE_LIVEDATA

    implementation("androidx.core","core-ktx","1.3.2") // CORE_KTX
    testImplementation("junit","junit","4.13") // JUNIT
    androidTestImplementation("androidx.test.ext","junit","1.1.2") // JUNIT_EXT

    //KOIN
    implementation("io.insert-koin","koin-core","3.2.0") // KOIN_CORE
    testImplementation("io.insert-koin","koin-test","3.2.0") // KOIN_TEST
    implementation("io.insert-koin","koin-android","3.2.0") // KOIN_ANDROID
    implementation("io.insert-koin","koin-android-compat","3.2.0") // KOIN_ANDROID_COMPAT
    implementation("io.insert-koin","koin-androidx-workmanager","3.2.0") // KOIN_WORKMANAGER
    implementation("io.insert-koin","koin-androidx-navigation","3.2.0") // KOIN_NAVIGATION
    implementation("io.insert-koin","koin-androidx-compose","3.2.0") // KOIN_COMPOSE

    implementation("com.google.android.material","material","1.2.1") // GOOGLE_MATERIAL
    // ROOM
    implementation("androidx.room","room-runtime","2.4.3") // ROOM_RUNTIME
    implementation("androidx.room","room-ktx","2.4.3") // ROOM_KTX
    // annotationProcessor("androidx.room","room-compiler","2.4.3") // ROOM_COMPILER
    kapt("androidx.room","room-compiler","2.4.3") // ROOM_COMPILER

    // MOSHI
    kapt("com.squareup.moshi","moshi-kotlin-codegen","1.11.0") // MOSHI_KOTLIN_CODEGEN
    implementation("com.squareup.moshi","moshi-kotlin","1.11.0") // MOSHI_KOTLIN
    implementation("com.squareup.moshi","moshi","1.11.0") // MOSHI
    // TIMBER
    implementation("com.jakewharton.timber","timber","4.7.1") // TIMBER
}