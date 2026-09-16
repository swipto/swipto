import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.library") {
                extensions.findByType<LibraryExtension>()?.let { configureAndroidCompose(it) }
            }
            pluginManager.withPlugin("com.android.application") {
                extensions.findByType<ApplicationExtension>()?.let { configureAndroidCompose(it) }
            }
        }
    }
}
