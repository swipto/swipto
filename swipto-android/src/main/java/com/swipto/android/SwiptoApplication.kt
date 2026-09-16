package com.swipto.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Base [Application] for Swipto apps. Configure DI via [swipto].
 *
 * ```
 * class MyApp : SwiptoApplication() {
 *   override fun swipto() = swiptoConfig {
 *     modules(appModule)
 *   }
 * }
 * ```
 */
abstract class SwiptoApplication : Application() {

    protected abstract fun swipto(): SwiptoConfig

    override fun onCreate() {
        super.onCreate()
        val config = swipto()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SwiptoApplication)
            modules(listOf(swiptoCoreModule) + config.modules)
        }
        onSwiptoReady()
    }

    /** Hook after Koin is started. */
    protected open fun onSwiptoReady() = Unit
}
