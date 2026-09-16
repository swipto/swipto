package com.swipto.sample.di

import com.swipto.sample.data.FakeSampleRepository
import com.swipto.sample.data.SampleRepository
import com.swipto.sample.ui.DetailViewModel
import com.swipto.sample.ui.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val sampleModule = module {
    single<SampleRepository> { FakeSampleRepository() }
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
