package com.dev.vapeshop.di

import android.app.Application
import com.dev.vapeshop.App
import com.dev.vapeshop.MainActivity
import com.dev.vapeshop.di.modules.AppModule
import com.dggorbachev.weatherapp.di.annotations.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

@Component(modules = [AndroidInjectionModule::class, AppModule::class])
@AppScope
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Application): AppComponent
    }

    fun app(): Application
    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
}