plugins { alias(libs.plugins.swipto.android.library) }
android { namespace = "com.swipto.network" }
dependencies {
    api(project(":swipto-core"))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
}
