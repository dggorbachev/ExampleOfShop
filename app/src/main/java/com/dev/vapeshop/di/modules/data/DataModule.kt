package com.dev.vapeshop.di.modules.data

import android.app.Application
import com.dev.vapeshop.features.preferences_manager.PreferencesManager
import com.dggorbachev.weatherapp.di.annotations.AppScope
import dagger.Module
import dagger.Provides

@Module(includes = [RemoteDataModule::class])
object DataModule {

    @Provides
    @AppScope
    fun providePreferencesManager(
        application: Application,
    ): PreferencesManager =
        PreferencesManager(application)
}