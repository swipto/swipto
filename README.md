# Swipto

Opinionated Kotlin Android app kit. You write screens, ViewModels, and modules against Swipto APIs; underneath you get **Jetpack Compose**, **ViewModel + StateFlow**, **Koin**, and **Navigation Compose**, with Gradle convention plugins aimed at faster incremental builds.

## Modules

| Module | Purpose |
|--------|---------|
| `swipto-core` | Pure Kotlin: `Outcome`, dispatchers, use-case contracts |
| `swipto-android` | `SwiptoApplication`, `SwiptoActivity`, Koin bootstrap |
| `swipto-ui` | `SwiptoViewModel`, `SwiptoScreen`, `SwiptoTheme` |
| `swipto-navigation` | Typed routes + `SwiptoNavHost` |
| `swipto-data` | Repository helpers + Retrofit/OkHttp factory |
| `sample-app` | End-to-end consumer demo (home + detail) |
| `build-logic` | Convention plugins for shared Android/Kotlin/Compose setup |

## Quick start (sample)

Requirements: JDK 17+, Android SDK (compile SDK 35).

```bash
./gradlew :sample-app:assembleDebug
```

On Windows:

```bat
gradlew.bat :sample-app:assembleDebug
```

Open the project in Android Studio and run `sample-app`.

## Write your first Swipto screen

### 1. Application + DI

```kotlin
class MyApp : SwiptoApplication() {
    override fun swipto() = swiptoConfig {
        modules(appModule)
    }
}

val appModule = module {
    single<MyRepository> { MyRepositoryImpl() }
    viewModel { HomeViewModel(get()) }
}
```

Register `MyApp` in the manifest (`android:name`).

### 2. ViewModel + screen

```kotlin
data class HomeState(
    val title: String = "",
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
) : SwiptoUiState

class HomeViewModel(
    private val repository: MyRepository,
) : SwiptoViewModel<HomeState>(HomeState(isLoading = true)) {
    init { refresh() }

    fun refresh() = launch {
        update { it.copy(isLoading = true) }
        // load via repository → Outcome
    }
}

@Composable
fun HomeScreen(vm: HomeViewModel = swiptoViewModel()) {
    SwiptoScreen(state = vm.state) { state ->
        Text(state.title)
    }
}
```

### 3. Navigation

```kotlin
object Routes {
    val Home = SwiptoSimpleRoute("home")
    val Detail = SwiptoSimpleRoute("detail")
}

SwiptoNavHost(
    start = Routes.Home,
    navController = navController,
    graph = SwiptoNavGraph {
        destination(Routes.Home) { HomeScreen(navController) }
        destination(Routes.Detail) { DetailScreen(navController) }
    },
)
```

### 4. Data

```kotlin
class MyRepositoryImpl : SwiptoRepository {
    suspend fun load(): Outcome<List<Item>> = repositoryCall {
        // network / db
    }
}

// Optional HTTP:
val retrofit = SwiptoHttp.retrofit("https://api.example.com/")
```

## Convention plugins (build speed)

Apply from any module:

- `swipto.android.application` — app module defaults (SDK, JVM 17, core deps)
- `swipto.android.library` — Android library defaults
- `swipto.android.compose` — Compose BOM + Material 3 + Compose compiler plugin
- `swipto.kotlin.jvm` — pure Kotlin JVM (used by `swipto-core`)

Shared settings in `gradle.properties`:

- `org.gradle.parallel=true`
- `org.gradle.caching=true`
- `org.gradle.configuration-cache=true`
- `android.nonTransitiveRClass=true`

Versions are centralized in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

## Consumer app wiring

In a multi-module consumer (or this repo’s `sample-app`):

```kotlin
plugins {
    alias(libs.plugins.swipto.android.application)
    alias(libs.plugins.swipto.android.compose)
}

dependencies {
    implementation(project(":swipto-ui"))
    implementation(project(":swipto-navigation"))
    implementation(project(":swipto-data"))
}
```

Include `build-logic` via `pluginManagement { includeBuild("build-logic") }` in `settings.gradle.kts` (already configured here).

## Design choices (v1)

- **Koin** instead of Hilt — no KAPT; lighter compile path
- **No code generation DSL** — libraries + conventions only
- **Min SDK 26**, compile/target **35**

## License

See repository license when published.
