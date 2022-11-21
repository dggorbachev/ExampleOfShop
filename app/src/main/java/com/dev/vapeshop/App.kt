package com.dev.vapeshop

import com.dev.vapeshop.di.AppComponent
import com.dev.vapeshop.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    private val component by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeDaggerComponent()
    }

    private fun initializeDaggerComponent(): AppComponent {
        component.inject(this)

        return component
    }
}