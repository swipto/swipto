plugins {
    alias(libs.plugins.swipto.android.library)
    alias(libs.plugins.swipto.android.compose)
}
android { namespace = "com.swipto.style" }
dependencies { api(project(":swipto-ui")) }
