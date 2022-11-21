package com.dev.vapeshop.di.modules.fragments

import com.dev.vapeshop.features.about_screen.ui.view.AboutFragment
import com.dev.vapeshop.features.cartridges_screen.ui.view.CartridgesFragment
import com.dev.vapeshop.features.devices_screen.ui.view.DevicesFragment
import com.dev.vapeshop.features.liquids_screen.ui.view.LiquidsFragment
import com.dev.vapeshop.features.settings_screen.ui.view.SettingsFragment
import com.dev.vapeshop.features.tobacco_screen.ui.view.TobaccoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeDevicesFragment(): DevicesFragment

    @ContributesAndroidInjector
    fun contributeLiquidsFragment(): LiquidsFragment

    @ContributesAndroidInjector
    fun contributeCartridgesFragment(): CartridgesFragment

    @ContributesAndroidInjector
    fun contributeTobaccoFragment(): TobaccoFragment

    @ContributesAndroidInjector
    fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    fun contributeAboutFragment(): AboutFragment
}