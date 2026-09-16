plugins {
    alias(libs.plugins.swipto.android.library)
}

android {
    namespace = "com.swipto.android"
}

dependencies {
    api(project(":swipto-runtime"))
    implementation(project(":swipto-core"))
    api(libs.koin.android)
    api(libs.androidx.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
}
