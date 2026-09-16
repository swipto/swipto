# Swipto performance guide

Swipto treats **runtime performance** and **developer/build performance** as different budgets. Do not claim an improvement from a dependency or API change without comparing the same device, Gradle version, JDK, Android SDK, and source revision.

## Runtime architecture

- `SwiptoViewModel` exposes one immutable `StateFlow`; `SwiptoScreen` uses lifecycle-aware collection. Emit only meaningful UI state changes, keep lists keyed, and move blocking work to injected I/O dispatchers.
- Framework styles are immutable declarations. Declare reusable styles at file scope or retain them with `remember`; do not rebuild declarations for every item in a lazy list.
- Navigation delegates to AndroidX Navigation Compose. Pass an existing `NavHostController` where a host needs advanced AndroidX behavior.
- `networkCall` and `repositoryCall` rethrow `CancellationException`. A cancelled screen request must not become an error state or continue background work.
- The networking, navigation, components, and styling modules are optional. Apps that do not include `swipto-network` do not resolve Retrofit, OkHttp, or Gson.

## Build architecture

The repository enables parallel execution, local build caching, configuration cache, file-system watching, Kotlin incremental compilation, classpath snapshots, and non-transitive Android R classes in `gradle.properties`.

Use `api` only for a dependency whose type appears in a public API. `implementation` keeps an internal library off downstream compile classpaths. In particular, Compose lifecycle collection and Koin Compose lookup are implementation details of `swipto-ui`; Retrofit/OkHttp/Gson remain inside `swipto-network`; no annotation processing or generated source is used by Swipto.

## Benchmark methodology

Record wall-clock duration, Gradle task outcomes, machine model, JDK, Gradle version, Android SDK, and commit SHA. Run each command three times after one warm-up; report median and range rather than a single run.

### Build benchmarks

```bash
# Clean build: delete outputs/caches relevant to the checkout before timing.
./gradlew clean :sample-app:assembleDebug --profile

# No-op build: run immediately after a successful build.
./gradlew :sample-app:assembleDebug --profile

# Kotlin/Compose incremental build: change one sample screen implementation, then restore it.
./gradlew :sample-app:assembleDebug --profile

# Configuration-cache reuse verification.
./gradlew :sample-app:assembleDebug --configuration-cache --configuration-cache-problems=fail
./gradlew :sample-app:assembleDebug --configuration-cache --configuration-cache-problems=fail
```

Use Build Scans where permitted, or `--profile` locally, to identify configuration, dependency resolution, Kotlin, and Compose compiler time separately. Do not compare clean and incremental builds as though they measure the same thing.

### Runtime benchmarks

Create a separate `:benchmark` Android Macrobenchmark module when the sample has a stable release-like build variant. Measure at least:

1. cold start to first frame and fully drawn home content;
2. home screen frame timing while rendering a representative list;
3. home-to-detail navigation frame timing and allocations;
4. repeated `StateFlow` updates for a representative screen state;
5. a cancelled request when navigating away, verifying no error UI is emitted.

Use `StartupTimingMetric`, `FrameTimingMetric`, `TraceSectionMetric`, and allocation metrics where a concrete regression is suspected. Run on a physical, thermally stable device with animations and network conditions controlled. Perfetto traces are the source of truth for a regression investigation.

## CI regression policy

On every pull request, run compilation and unit tests with configuration-cache problems set to `fail`, then run a no-op build to verify cache reuse. On a scheduled or protected-branch workflow, run the clean and incremental sample builds and upload their task/profile reports. Run Macrobenchmarks on dedicated hardware nightly or before release; compare medians against a stored baseline and alert on a sustained regression (for example, more than 10% across three comparable runs). Treat results as signals requiring trace investigation, not automatic proof of a framework regression.
