import java.io.FilenameFilter
import java.nio.charset.Charset

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


dependencies {
    // COMPOSE
    implementation("androidx.activity", "activity-compose", "1.5.1")
    implementation("androidx.compose.material", "material", "1.2.1")
    implementation("androidx.compose.animation", "animation", "1.2.1")
    implementation("androidx.compose.ui", "ui-tooling", "1.2.1")
    implementation("androidx.compose.ui", "ui-tooling-preview", "1.2.1")
    debugImplementation("androidx.compose.ui", "ui-test-manifest", "1.2.1")
    implementation("androidx.lifecycle", "lifecycle-viewmodel-compose", "2.5.1")
    implementation("androidx.navigation", "navigation-compose", "2.5.1")
    // inofficial, experimental ViewPager
    implementation("com.google.accompanist:accompanist-pager:0.22.0-rc")
    // Test
    testImplementation("androidx.compose.ui", "ui-test-junit4", "1.2.1")
    androidTestImplementation("androidx.test", "runner", "1.4.0")
    androidTestImplementation("androidx.test", "rules", "1.4.0")
    androidTestImplementation("androidx.test", "core", "1.4.0")


    // LIFECYCLE
    implementation("androidx.lifecycle", "lifecycle-extensions", "2.2.0") // LIFECYCLE_EXTENSIONS
    implementation("androidx.lifecycle", "lifecycle-viewmodel-ktx", "2.2.0") // LIFECYCLE_VIEWMODEL
    implementation("androidx.lifecycle", "lifecycle-livedata-ktx", "2.2.0") // LIFECYCLE_LIVEDATA

    implementation("androidx.core", "core-ktx", "1.3.2") // CORE_KTX
    testImplementation("junit", "junit", "4.13") // JUNIT
    androidTestImplementation("androidx.test.ext", "junit", "1.1.3") // JUNIT_EXT

    //KOIN
    implementation("io.insert-koin", "koin-core", "3.2.2") // KOIN_CORE
    testImplementation("io.insert-koin", "koin-test", "3.2.2") // KOIN_TEST
    testImplementation("io.insert-koin", "koin-test-jvm", "3.2.2") // KOIN_TEST
    testImplementation("io.insert-koin", "koin-test-junit4", "3.2.2")

    implementation("io.insert-koin", "koin-android", "3.2.0") // KOIN_ANDROID
    implementation("io.insert-koin", "koin-android-compat", "3.2.0") // KOIN_ANDROID_COMPAT
    implementation("io.insert-koin", "koin-androidx-workmanager", "3.2.0") // KOIN_WORKMANAGER
    implementation("io.insert-koin", "koin-androidx-navigation", "3.2.0") // KOIN_NAVIGATION
    implementation("io.insert-koin", "koin-androidx-compose", "3.2.0") // KOIN_COMPOSE

    implementation("com.google.android.material", "material", "1.2.1") // GOOGLE_MATERIAL
    // ROOM
    implementation("androidx.room", "room-runtime", "2.4.3") // ROOM_RUNTIME
    implementation("androidx.room", "room-ktx", "2.4.3") // ROOM_KTX
    // annotationProcessor("androidx.room","room-compiler","2.4.3") // ROOM_COMPILER
    kapt("androidx.room", "room-compiler", "2.4.3") // ROOM_COMPILER

    // MOSHI
    kapt("com.squareup.moshi", "moshi-kotlin-codegen", "1.11.0") // MOSHI_KOTLIN_CODEGEN
    implementation("com.squareup.moshi", "moshi-kotlin", "1.11.0") // MOSHI_KOTLIN
    implementation("com.squareup.moshi", "moshi", "1.11.0") // MOSHI
    // TIMBER
    implementation("com.jakewharton.timber", "timber", "4.7.1") // TIMBER
}

tasks.register("generateImageMap") {
    println("generateImageMap")
    val pathForImageExtention = "$rootDir/app/src/main/java/redtoss/example/furstychristmas/ui/util/InfoPageUtil.kt"
    val pathForDrawables = "$rootDir/app/src/main/res/drawable-v24/"

    val map = mutableMapOf<String, String>()
    try {
        val files = File(pathForDrawables).listFiles(FilenameFilter { _, name -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") })
        files.forEach {
            val newName = it.name.replace(".png", "").replace(".jpg", "").replace(".jpeg", "")
            map.put(newName, "R.drawable.$newName")
        }
    } catch (e: Exception) {
        println("could not read drawable ressources")
    }
    val mapText = map.entries.joinToString(separator = "\n") { "\t\t\"${it.key}\" -> ${it.value}" }
    try {
        val file = File(pathForImageExtention)
        if (!file.parentFile.exists()) {
            file.parentFile.createNewFile()
            println("${file.parentFile} created")
        }
        if (file.exists()) {
            file.delete()
            println("${file} deleted")
        }
        if (!file.exists()) {
            file.createNewFile()
            println("${file} created")
        }
        val content = """
package redtoss.example.furstychristmas.ui.util

import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import timber.log.Timber

/**
* This utility method is generated by gradle task '$name'
*/
internal fun InfoPageContent.resolveImageId(imageid: String?): Int? {
    return when (imageid) {
$mapText
        else -> {
            if (!imageid.isNullOrBlank()) {
                Timber.w("Imageid: ${'$'}imageid could not be resolved!")
            }
            null
        }
    }
}
    """

        file.writeText(text = content, charset = Charset.defaultCharset())
    } catch (e: Exception) {
        println("creating Mapping for Images failed: $e")
    }
}

tasks.register("checkImageMap") {
    dependsOn("generateImageMap")
    shouldRunAfter("generateImageMap")
    println("checkImageMap")
    doLast {
        val mapFunctionFile = File("$rootDir/app/src/main/java/redtoss/example/furstychristmas/ui/util/InfoPageUtil.kt")
        val infoJsonFiles = File("$rootDir/app/src/main/assets/").listFiles(FilenameFilter { _, name -> name.endsWith("_info.json") })
        val imagesInJsonFiles = mutableSetOf<String>()
        val imagesInFunction = mutableSetOf<String>()
        infoJsonFiles.forEach { file ->
            file.useLines() { lines ->
                lines.filter { it.contains("\"imageid\"") }.forEach {
                    it.split(":").forEach { line ->
                        val existingStringRes = line.replace("\"imageid\"", "").replace(",", "").replace("\"", "").trim()
                        if (!existingStringRes.isNullOrBlank()) {
                            imagesInJsonFiles.add(existingStringRes)
                        }
                    }
                }
            }
        }
        mapFunctionFile.useLines { lines ->
            val images = lines
                .filter { it.contains("->") && !it.contains("else") }
                .map { it.split("->")[0].replace(",", "").replace("\"", "").trim() }
                .toList()
            imagesInFunction.addAll(images)
        }

        if (imagesInFunction.size < imagesInJsonFiles.size) {
            val unmappedImages = mutableSetOf(imagesInJsonFiles)
            unmappedImages.removeAll(imagesInFunction)
            println("Not all images are mapped by function. The following Images are not mapped: ${unmappedImages.joinToString()}")
            println("From Json Files   : ${imagesInJsonFiles.sorted().joinToString()}")
            println("From Mapped values: ${imagesInFunction.sorted().joinToString()}")
        }
    }
}
