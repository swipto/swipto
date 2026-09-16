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
