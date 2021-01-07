plugins {
    id("com.android.application")
    kotlin("android")
//    kotlin("android.extensions")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.temas.netlobby.android"
        minSdkVersion(15)
        targetSdkVersion(30)
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
    implementation("org.koin:koin-core:$koinVersion")
    testImplementation("org.koin:koin-test:$koinVersion")
}