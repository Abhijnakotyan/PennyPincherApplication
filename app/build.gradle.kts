plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Kotlin plugin
}

android {
    namespace = "com.example.pennypincherapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pennypincherapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)           // Correct reference
    implementation(libs.androidx.appcompat)          // Correct reference
    implementation(libs.material)                    // Correct reference
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.android.holo.graph)
    implementation(libs.mpandroidchart)
    implementation(libs.holographlibrary)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
