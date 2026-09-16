plugins {
    alias(libs.plugins.swipto.android.library)
    alias(libs.plugins.swipto.android.compose)
}
android { namespace = "com.swipto.components" }
dependencies {
    api(project(":swipto-ui"))
    api(project(":swipto-style"))
}
