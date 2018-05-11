package com.abaffy.dailyflights.di

import com.abaffy.dailyflights.ui.list.MainActivity
import com.abaffy.dailyflights.ui.detail.FlightDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        FragmentBuildersModule::class
    ])
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [
        FragmentBuildersModule::class
    ])
    internal abstract fun flightDetailActivity(): FlightDetailActivity


}