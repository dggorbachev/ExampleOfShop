package com.dev.vapeshop.di.modules

import com.dev.vapeshop.di.modules.activity.ActivityBuildersModule
import com.dev.vapeshop.di.modules.data.DataModule
import dagger.Module

@Module(
    includes = [
        ActivityBuildersModule::class,
        DataModule::class
    ]
)
class AppModule
