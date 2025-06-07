plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)

}

android {
    namespace = "com.apprajapati.rapidcents"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.apprajapati.rapidcents"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    //Coil
    implementation(libs.coil.compose)
    //Retrofit
    implementation(libs.retrofit)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    //GSON converter
    implementation(libs.converter.gson)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.truth)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Nav3
    //Core nav3 api. Includes NavEntry, Entry provider, and DSL
    implementation(libs.androidx.navigation3.runtime)
    //provides classes to display content, including NavDisplay and Scene
    implementation(libs.androidx.navigation3.ui)
    //allows view models to be scoped to entries in the back stack
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}