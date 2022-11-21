package com.dev.vapeshop.di.modules.activity

import com.dev.vapeshop.MainActivity
import com.dev.vapeshop.di.modules.fragments.FragmentsBuilderModule
import com.dggorbachev.weatherapp.di.annotations.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentsBuilderModule::class])
    fun contributeMainActivity(): MainActivity
}