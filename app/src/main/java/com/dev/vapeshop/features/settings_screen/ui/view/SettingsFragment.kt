package com.dev.vapeshop.features.settings_screen.ui.view

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dev.vapeshop.R
import com.dev.vapeshop.base.BaseFragment
import com.dev.vapeshop.base.LambdaFactory
import com.dev.vapeshop.databinding.FragmentSettingsBinding
import com.dev.vapeshop.features.preferences_manager.Theme
import com.dev.vapeshop.features.preferences_manager.Theme.*
import com.dev.vapeshop.features.settings_screen.stateholders.SettingsViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    @Inject
    lateinit var factory: SettingsViewModel.Factory

    private val viewModel: SettingsViewModel by viewModels {
        LambdaFactory(this) { stateHandle ->
            factory.create(
                stateHandle
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindThemeListener()
        bindTheme()
    }

    private fun bindThemeListener() {
        binding.themeGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.themeLight -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
                R.id.themeDark -> setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
                R.id.themeSystem -> setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                    THEME_SYSTEM)
            }
        }
    }

    private fun setTheme(themeMode: Int, themePrefs: Theme) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        viewModel.updateTheme(themePrefs)
    }

    private fun bindTheme() {
        with(binding) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                themeSystem.visibility = View.VISIBLE
            } else {
                themeSystem.visibility = View.GONE
            }

            lifecycleScope.launch {
                when (viewModel.getTheme()) {
                    THEME_SYSTEM -> {
                        themeSystem.isChecked = true
                    }
                    THEME_LIGHT -> {
                        themeLight.isChecked = true
                    }
                    THEME_DARK -> {
                        themeDark.isChecked = true
                    }
                    THEME_UNDEFINED -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            themeSystem.isChecked = true
                        } else {
                            when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                                Configuration.UI_MODE_NIGHT_NO -> themeLight.isChecked = true
                                Configuration.UI_MODE_NIGHT_YES -> themeDark.isChecked = true
                                Configuration.UI_MODE_NIGHT_UNDEFINED -> themeLight.isChecked = true
                            }
                        }
                    }
                }
            }
        }
    }
}