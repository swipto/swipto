plugins { alias(libs.plugins.swipto.android.library) }
android { namespace = "com.swipto.storage" }
dependencies {
    api(project(":swipto-core"))
    api(libs.kotlinx.coroutines.core)
}
