plugins {
    alias(libs.plugins.swipto.android.library)
}

android {
    namespace = "com.swipto.data"
}

dependencies {
    api(project(":swipto-core"))
    api(libs.kotlinx.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
}
