package com.swipto.sample

import com.swipto.android.SwiptoApplication
import com.swipto.android.swiptoConfig
import com.swipto.sample.di.sampleModule

class SampleApp : SwiptoApplication() {
    override fun swipto() = swiptoConfig {
        modules(sampleModule)
    }
}
