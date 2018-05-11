package com.abaffy.dailyflights.di

import com.abaffy.dailyflights.ui.detail.FlightDetailFragment
import com.abaffy.dailyflights.ui.list.FlightListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun flightDetailFragment(): FlightDetailFragment

    @ContributesAndroidInjector
    internal abstract fun flightListFragment(): FlightListFragment

}