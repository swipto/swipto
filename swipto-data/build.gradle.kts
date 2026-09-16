plugins {
    alias(libs.plugins.swipto.android.library)
}

android {
    namespace = "com.swipto.data"
}

dependencies {
    api(project(":swipto-core"))
    api(libs.kotlinx.coroutines.core)
}
