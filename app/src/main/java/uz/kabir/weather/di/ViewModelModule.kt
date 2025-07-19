package uz.kabir.weather.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uz.kabir.weather.presentation.screen.viewmodel.AddViewModel
import uz.kabir.weather.presentation.screen.viewmodel.MainViewModel

val viewModelModule = module {
    viewModel { AddViewModel(get(), get(), get(), get(), get())  }
    viewModel { MainViewModel(get(), get(), get(), get()) }
}