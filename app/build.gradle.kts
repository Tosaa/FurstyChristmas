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
    composeOptions{
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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
    implementation(libs.androidx.appcompat)

    // COMPOSE
    implementation(libs.andoidx.activity.compose)
    api(platform("androidx.compose:compose-bom:2024.09.03"))
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.ui )
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.navigation)

    // Test
    implementation(libs.androidx.ui.test.junit)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.core)


    // LIFECYCLE
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.ext.junit)

    //KOIN
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.core.viewmodel)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.jvm)
    testImplementation(libs.koin.test.junit4)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compat)

    implementation(libs.koin.androidx.workmanager)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

    // GOOGLE_MATERIAL
    implementation(libs.google.material)

    // ROOM
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // TIMBER
    implementation(libs.timber)
    implementation(project(":calendarcontent"))
}
repositories {
    mavenCentral()
    google()
}

tasks.register<ImageMappingTask>("ImageMappingTask") {
    this.folderWithDrawables.set(File("$rootDir/app/src/main/res/drawable.v24/"))
}

tasks.register<CheckImageMappingTask>("CheckImageMappingTask") {
    dependsOn("ImageMappingTask")
    shouldRunAfter("ImageMappingTask")
    this.infoPageUtilFile.set(File("$rootDir/app/src/main/java/redtoss/example/furstychristmas/ui/util/InfoPageUtil.kt"))
    this.folderWithInfoJsonFiles.set(File("$rootDir/app/src/main/assets/"))
}
