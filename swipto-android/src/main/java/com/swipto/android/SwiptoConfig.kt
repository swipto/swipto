package com.swipto.android

import org.koin.core.module.Module
import org.koin.dsl.module
import com.swipto.core.DefaultSwiptoDispatchers
import com.swipto.core.SwiptoDispatchers

/**
 * Configuration supplied by the host [SwiptoApplication].
 */
class SwiptoConfig internal constructor(
    val modules: List<Module>,
)

/**
 * DSL entry used from [SwiptoApplication.swipto].
 */
fun swiptoConfig(block: SwiptoConfigBuilder.() -> Unit): SwiptoConfig =
    SwiptoConfigBuilder().apply(block).build()

class SwiptoConfigBuilder {
    private val modules = mutableListOf<Module>()

    fun modules(vararg module: Module) {
        modules += module
    }

    fun modules(moduleList: List<Module>) {
        modules += moduleList
    }

    internal fun build(): SwiptoConfig = SwiptoConfig(modules.toList())
}

/**
 * Core Koin module provided by the kit.
 */
val swiptoCoreModule: Module = module {
    single<SwiptoDispatchers> { DefaultSwiptoDispatchers() }
}
