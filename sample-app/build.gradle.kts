plugins {
    alias(libs.plugins.swipto.android.application)
    alias(libs.plugins.swipto.android.compose)
}

android {
    namespace = "com.swipto.sample"

    defaultConfig {
        applicationId = "com.swipto.sample"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":swipto-android"))
    implementation(project(":swipto-ui"))
    implementation(project(":swipto-components"))
    implementation(project(":swipto-style"))
    implementation(project(":swipto-navigation"))
    implementation(project(":swipto-data"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.androidx.compose)
}
