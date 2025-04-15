plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"

    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.google.android.googlevideodiscovery.common"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    api("androidx.core:core-ktx:1.16.0")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    api("androidx.activity:activity-compose:1.10.1")
    api("androidx.appcompat:appcompat:1.7.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Room db
    val roomVersion = "2.7.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Work
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    // Compose
    api(platform(libs.androidx.compose.bom))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.foundation:foundation")
    api("androidx.compose.material:material-icons-core")
    api("androidx.compose.material:material-icons-extended")
}