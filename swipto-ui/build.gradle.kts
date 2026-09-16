plugins {
    alias(libs.plugins.swipto.android.library)
    alias(libs.plugins.swipto.android.compose)
}

android {
    namespace = "com.swipto.ui"
}

dependencies {
    api(project(":swipto-style"))
    api(project(":swipto-runtime"))
    implementation(project(":swipto-android"))
    api(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.kotlinx.coroutines.android)
}
