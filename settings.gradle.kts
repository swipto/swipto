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
include(":swipto-runtime")
include(":swipto-android")
include(":swipto-ui")
include(":swipto-style")
include(":swipto-components")
include(":swipto-navigation")
include(":swipto-data")
include(":swipto-network")
include(":swipto-storage")
include(":sample-app")
