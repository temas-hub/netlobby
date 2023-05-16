plugins {
    id("com.android.application")
    kotlin("android")
//    kotlin("android.extensions")
}

android {
    namespace = "com.temas.netlobby"
    compileSdk = 31
    defaultConfig {
        applicationId = "com.temas.netlobby.android"
        minSdk = 15
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        unitTests.apply {
            isIncludeAndroidResources = true
        }
    }
}

val koinVersion : String by rootProject

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.insert-koin:koin-android:$koinVersion")
}