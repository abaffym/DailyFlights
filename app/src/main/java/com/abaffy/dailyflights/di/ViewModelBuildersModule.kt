package com.abaffy.dailyflights.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.abaffy.dailyflights.ui.detail.FlightDetailViewModel
import com.abaffy.dailyflights.ui.list.FlightListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuildersModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FlightDetailViewModel::class)
    abstract fun bindFlightDetailViewModel(viewModel: FlightDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightListViewModel::class)
    abstract fun bindFlightListViewModel(viewModel: FlightListViewModel): ViewModel

}