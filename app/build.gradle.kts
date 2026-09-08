import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")

    id("org.jetbrains.kotlin.plugin.serialization")
}

//Local Properties (Pexel API Key)
val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
val pexelsApiKey = localProperties.getProperty("PEXELS_API_KEY") ?: ""


android {
    namespace = "com.furkanfidanoglu.artbooktesting"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.furkanfidanoglu.artbooktesting"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.furkanfidanoglu.artbooktesting.di.HiltTestRunner"

        buildConfigField(
            "String",
            "PEXELS_API_KEY",
            "\"$pexelsApiKey\""
        )
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")

            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

// Testing
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.11.0")

    testImplementation("com.google.truth:truth:1.4.5")
    androidTestImplementation("com.google.truth:truth:1.4.5")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.60.1")
    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.60.1")

    testImplementation("org.mockito.kotlin:mockito-kotlin:6.3.0")

// Navigation Compose - Serialization
    implementation("androidx.navigation:navigation-compose:2.9.8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

// Lifecycle / ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.11.0")

// Hilt
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-android-compiler:2.60.1")

// Hilt + Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.4.0")

// Room
    implementation("androidx.room3:room3-runtime:3.0.1")
    ksp("androidx.room3:room3-compiler:3.0.1")

// Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")

// Gson - Convertor
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

// API'den gelen resimleri Compose'da göstermek için Coil
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.5.0")

// Icons
    implementation("androidx.compose.material:material-icons-extended")
}