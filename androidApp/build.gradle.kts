plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version Deps.kotlinVersion
//    id("org.jetbrains.kotlin.plugin.serialization")
//    kotlin("jvm") version "1.9.0"
}

android {
    namespace = "com.example.ula_app.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.ula_app.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Deps.composeUi)
    implementation(Deps.composeUiTooling)
    implementation(Deps.composeUiToolingPreview)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeMaterial)
    implementation(Deps.activityCompose)
    implementation(Deps.composeIconsExtended)
    implementation(Deps.composeNavigation)
    implementation(Deps.composeLifecycle)
    implementation(Deps.coilCompose)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")
    implementation(Deps.kotlinDateTime)
    implementation ("androidx.compose.runtime:runtime-livedata:1.0.3")

    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltAndroidCompiler)
    kapt(Deps.hiltCompiler)
    implementation(Deps.hiltNavigationCompose)

    implementation(Deps.ktorAndroid)

    androidTestImplementation(Deps.testRunner)
    androidTestImplementation(Deps.jUnit)
    androidTestImplementation(Deps.composeTesting)
    debugImplementation(Deps.composeTestManifest)

    kaptAndroidTest(Deps.hiltAndroidCompiler)
    androidTestImplementation(Deps.hiltTesting)

    implementation ("androidx.media3:media3-exoplayer:1.0.2")
    implementation ("androidx.media3:media3-exoplayer-dash:1.0.2")
    implementation ("androidx.media3:media3-ui:1.0.2")

    // tab row
    implementation("com.google.accompanist:accompanist-pager:0.28.0") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:0.28.0") // Pager Indicators


    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation(Deps.kotlinSerialization)

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.8.0"))

    // stats line plot
    // Includes the core logic for charts and other elements.
    implementation("com.patrykandpatrick.vico:core:1.7.1")

    // For Jetpack Compose.
    implementation("com.patrykandpatrick.vico:compose:1.7.1")

    // For the view system.
    implementation("com.patrykandpatrick.vico:views:1.7.1")

    // For `compose`. Creates a `ChartStyle` based on an M2 Material Theme.
    implementation("com.patrykandpatrick.vico:compose-m3:1.7.1")

    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("com.mutualmobile:composesensors:1.1.0")

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.2.0")
    implementation ("androidx.browser:browser:1.4.0")
}