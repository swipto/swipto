plugins {
    alias(libs.plugins.swipto.android.library)
    alias(libs.plugins.swipto.android.compose)
}

android {
    namespace = "com.swipto.navigation"
}

dependencies {
    api(libs.androidx.navigation.compose)
}
