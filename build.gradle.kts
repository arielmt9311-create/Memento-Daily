plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ariel.mementoestoico"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.ariel.mementoestoico"
        minSdk = 26
        targetSdk = 36
        versionCode = 3
        versionName = "3.0"
    }
}

kotlin {
    jvmToolchain(17)
}
