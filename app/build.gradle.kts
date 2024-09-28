import org.gradle.api.artifacts.dsl.Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}


android {
    compileSdk = 34
    defaultConfig {
        applicationId = "redtoss.example.furstychristmas"
        namespace = "redtoss.example.furstychristmas"
        minSdk = 26
        targetSdk = 33
        versionCode = 3
        versionName = "2.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

composeCompiler {
    // No compose compiler settings applied

}


dependencies {
    // COMPAT
    implementation("androidx.appcompat", "appcompat", "1.7.0")

    // COMPOSE
    implementation("androidx.activity", "activity-compose", "1.9.2")
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))
    implementation("androidx.compose.material", "material")
    implementation("androidx.compose.material", "material-icons-core")
    implementation("androidx.compose.animation", "animation")
    implementation("androidx.compose.ui", "ui")
    implementation("androidx.compose.ui", "ui-tooling-preview")
    debugImplementation("androidx.compose.ui", "ui-test-manifest")
    implementation("androidx.lifecycle", "lifecycle-viewmodel-compose", "2.8.6")
    implementation("androidx.navigation", "navigation-compose", "2.8.1")
    // inofficial, experimental ViewPager
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    // Test
    testImplementation("androidx.compose.ui", "ui-test-junit4")
    androidTestImplementation("androidx.test", "runner", "1.6.2")
    androidTestImplementation("androidx.test", "rules", "1.6.1")
    androidTestImplementation("androidx.test", "core", "1.6.1")


    // LIFECYCLE
    implementation("androidx.lifecycle", "lifecycle-viewmodel-ktx", "2.8.6") // LIFECYCLE_VIEWMODEL
    implementation("androidx.lifecycle", "lifecycle-livedata-ktx", "2.8.6") // LIFECYCLE_LIVEDATA

    implementation("androidx.core", "core-ktx", "1.13.1") // CORE_KTX
    testImplementation("junit", "junit", "4.13") // JUNIT
    androidTestImplementation("androidx.test.ext", "junit", "1.1.3") // JUNIT_EXT

    //KOIN
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.0"))
    implementation("io.insert-koin", "koin-core") // KOIN_CORE
    implementation("io.insert-koin", "koin-core-viewmodel") // KOIN_CORE
    testImplementation("io.insert-koin", "koin-test") // KOIN_TEST
    testImplementation("io.insert-koin", "koin-test-jvm") // KOIN_TEST
    testImplementation("io.insert-koin", "koin-test-junit4")

    implementation("io.insert-koin", "koin-android") // KOIN_ANDROID
    implementation("io.insert-koin", "koin-android-compat") // KOIN_ANDROID_COMPAT
    implementation("io.insert-koin", "koin-androidx-workmanager") // KOIN_WORKMANAGER
    implementation("io.insert-koin", "koin-androidx-navigation") // KOIN_NAVIGATION
    implementation("io.insert-koin", "koin-androidx-compose") // KOIN_COMPOSE
    implementation("io.insert-koin", "koin-androidx-compose-navigation") // KOIN_COMPOSE_NAVIGATION

    implementation("com.google.android.material", "material", "1.12.0") // GOOGLE_MATERIAL
    // ROOM
    implementation("androidx.room", "room-runtime", "2.6.1") // ROOM_RUNTIME
    implementation("androidx.room", "room-ktx", "2.6.1") // ROOM_KTX
    ksp("androidx.room", "room-compiler", "2.6.1") // ROOM_COMPILER

    // MOSHI

    ksp("com.squareup.moshi", "moshi-kotlin-codegen", "1.15.1") // MOSHI_KOTLIN_CODEGEN
    implementation("com.squareup.moshi", "moshi-kotlin", "1.15.1") // MOSHI_KOTLIN
    implementation("com.squareup.moshi", "moshi", "1.15.1") // MOSHI
    // TIMBER
    implementation("com.jakewharton.timber", "timber", "5.0.1") // TIMBER
}

tasks.register<ImageMappingTask>("ImageMappingTask") {
    this.folderWithDrawables.set(File("$rootDir/app/src/main/res/drawable-v24/"))
}

tasks.register<CheckImageMappingTask>("CheckImageMappingTask") {
    dependsOn("ImageMappingTask")
    shouldRunAfter("ImageMappingTask")
    this.infoPageUtilFile.set(File("$rootDir/app/src/main/java/redtoss/example/furstychristmas/ui/util/InfoPageUtil.kt"))
    this.folderWithInfoJsonFiles.set(File("$rootDir/app/src/main/assets/"))
}
