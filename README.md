# Swipto

Swipto is a Kotlin-first Android application framework layered over Jetpack Compose. It reduces repeated bootstrap, state, navigation, async UI, styling, and component wiring while leaving normal Kotlin and Compose APIs available.

## Module map

| Module | Purpose |
|---|---|
| `swipto-core` | Pure Kotlin outcomes, dispatchers, and use-case contracts. |
| `swipto-runtime` | Platform-independent `UiState`, `LoadState`, effects, and state store contracts. |
| `swipto-android` | Application and activity integration. |
| `swipto-ui` | Compose app host, screens, async content, and adaptive layouts. |
| `swipto-style` | Immutable, token-friendly Kotlin styles applied to `Modifier`. |
| `swipto-components` | Optional high-level Material 3 components. |
| `swipto-navigation` | Navigation Compose bridge and navigator abstraction. |
| `swipto-data` | Repository helpers and data contracts. |
| `swipto-network` | Optional Retrofit/OkHttp networking defaults. |
| `swipto-storage` | Typed key-value storage contract. |
| `sample-app` | End-to-end reference app. |

## Quick start

```kotlin
class MyApp : SwiptoApplication() {
    override fun swipto() = swiptoConfig { modules(appModule) }
}

class MainActivity : SwiptoActivity() {
    override fun Content() = setContent {
        SwiptoApp {
            AppColumn(style = AppStyles.page) {
                AppText("Hello")
                AppButton(text = "Continue", onClick = ::continueFlow)
            }
        }
    }
}
```

`SwiptoApp`, styles, and components are optional convenience layers. You can use normal Compose `Modifier`, Material 3 components, AndroidX Navigation, `StateFlow`, and existing Koin modules whenever they are the better fit.

## Application development model

Swipto is opinionated about the repeated seams of an Android application, not about hiding Android. It has three API levels:

1. **Level 1 — defaults:** `SwiptoApplication`, `SwiptoActivity`, `SwiptoApp`, `SwiptoScaffold`, and the ViewModel `SwiptoScreen` overload remove repeated host, theme, scaffold, Koin lookup, and lifecycle collection code.
2. **Level 2 — framework building blocks:** `SwiptoViewModel`, `SwiptoScreen(StateFlow)`, `SwiptoNavHost`, `SwiptoRepository`, `networkCall`, `rememberFormField`, and styles make those conventions explicit when a screen needs control.
3. **Level 3 — native escape hatches:** every wrapper accepts or exposes Compose `Modifier`, `StateFlow`, AndroidX `NavHostController`, Koin modules, Retrofit/OkHttp builders, and ordinary Kotlin coroutines. Use `Scaffold`, `collectAsStateWithLifecycle`, `NavHost`, or Retrofit directly whenever they describe the requirement better.

### Boilerplate inventory

| Repetitive concern | Current Android/Compose code | Swipto default | What happens internally / tradeoff |
|---|---|---|---|
| Application initialization and DI | `Application.onCreate { startKoin { androidContext(...); modules(...) } }` | `class App : SwiptoApplication() { override fun swipto() = swiptoConfig { modules(appModule) } }` | Starts Koin with Android context and framework dispatchers. Koin remains visible and replaceable. |
| Activity, edge-to-edge, and theme | `ComponentActivity`, `enableEdgeToEdge`, `setContent`, `MaterialTheme` | `SwiptoActivity` + `setContent { SwiptoApp { … } }` | The base activity enables edge-to-edge; the app host supplies tokens and light/dark theme. The activity still owns Android lifecycle APIs. |
| Screen scaffold | `Scaffold { padding -> NavHost(Modifier.padding(padding)) }` | `SwiptoScaffold { padding -> … }` | Provides the standard Material 3 chrome and optional title. Use a native `Scaffold` for bespoke chrome. |
| ViewModel and state collection | `val vm = koinViewModel<…>(); val state by vm.state.collectAsStateWithLifecycle()` | `SwiptoScreen<MyVm, MyState> { vm, state -> … }` | Looks up the Koin ViewModel and collects once lifecycle-aware. The explicit `StateFlow` overload remains available. |
| Loading and errors | add progress/error overlays per screen | state implements `SwiptoUiState`; render via `SwiptoScreen` | Renders consistent overlays. Domain-specific empty/error presentation stays in screen content. |
| Navigation | manually create a controller and register AndroidX destinations | `SwiptoNavGraph { destination(Route) { … } }` + `SwiptoNavHost` | Wraps an AndroidX controller, without replacing it; `NavHostController` is accepted for interop. |
| API and repositories | repeat `try/catch` around Retrofit calls | `networkCall { api.get…() }`, `repositoryCall { … }`, `Outcome` | Converts throwing work to a typed result. It deliberately does not impose serialization, auth, or caching policy. |
| Forms and validation | local mutable value/touched/error state per field | `rememberFormField(validator = …)` + `FormTextField` | Holds field interaction state and invokes ordinary Kotlin validation. Complex forms should retain a screen ViewModel. |

### Screen before and after

```kotlin
// Plain Compose + Koin: lookup and collection repeat in every screen.
val viewModel: HomeViewModel = koinViewModel()
val state by viewModel.state.collectAsStateWithLifecycle()
SwiptoScreen(state) { current -> HomeContent(current, onRefresh = viewModel::refresh) }

// Swipto Level 1: one framework call.
SwiptoScreen<HomeViewModel, HomeState> { viewModel, state ->
    HomeContent(state, onRefresh = viewModel::refresh)
}
```

The Level 1 version removes two recurring setup statements per screen while keeping state ownership, state type, and actions compile-time visible. It does not use reflection, generated route parsing, or runtime configuration parsing.

### Performance and build implications

Styles and theme tokens are immutable; responsive selection only observes the window width class, and state screens collect their `StateFlow` with `collectAsStateWithLifecycle`. The framework does not add a runtime DI container beyond Koin already selected by the app, nor does it add annotation processing or code generation. The Level 1 APIs are thin composable delegates, so advanced code can move down a level without a migration boundary.

### Migration strategy

Adopt incrementally: wrap an existing `setContent` block in `SwiptoApp`, replace one screen's ViewModel lookup/collection with `SwiptoScreen<VM, State>`, then introduce `SwiptoScaffold` and `SwiptoUiState` where the default loading/error behavior fits. Existing Compose screens, AndroidX navigation graphs, Koin modules, Retrofit services, and `Modifier` chains continue to work unchanged.

## Styles

`swipto-style` is a Kotlin DSL that composes native Compose modifiers; it does not parse CSS or replace Compose layout.

```kotlin
val card = style {
    layout { padding(16.dp); fillWidth() }
    visual { radius(16.dp); elevation(6.dp) }
}
val themedSurface = themedStyle { visual { background(ColorToken.Surface) } }

val responsiveCard = responsiveStyle {
    compact { layout { padding(12.dp) } }
    medium { layout { padding(20.dp) } }
    expanded { layout { padding(32.dp) } }
}

Box(Modifier.style(card + responsiveCard.resolveCurrent()).style(themedSurface))
```

Use `statefulStyle` for normal, pressed, focused, disabled, selected, and hovered variants, and resolve it from the component's interaction state. `SwiptoTheme` provides `LocalThemeTokens` with centralized colors, typography, spacing, radii, and elevation values; its color scheme changes automatically for dark mode. Keep reusable style declarations outside composables (or in `remember`) and continue using ordinary `Modifier` calls for one-off behavior.

## Build

Requirements: JDK 17+ and Android SDK 35.

```bash
bash ./gradlew :sample-app:assembleDebug
```

The project uses Kotlin 2.0.21, AGP 8.7.3, Gradle 8.9, and min SDK 26. Versions are centralized in `gradle/libs.versions.toml`.

See [the performance guide](docs/performance.md) for separate runtime/build risks, dependency boundaries, reproducible benchmarks, and CI regression policy.
