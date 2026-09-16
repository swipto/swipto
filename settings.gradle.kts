pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "swipto"

include(":swipto-core")
include(":swipto-android")
include(":swipto-ui")
include(":swipto-navigation")
include(":swipto-data")
include(":sample-app")
