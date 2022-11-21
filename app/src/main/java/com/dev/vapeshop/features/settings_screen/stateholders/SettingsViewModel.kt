package com.dev.vapeshop.features.settings_screen.stateholders

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.vapeshop.features.liquids_screen.stateholders.LiquidsViewModel
import com.dev.vapeshop.features.preferences_manager.PreferencesManager
import com.dev.vapeshop.features.preferences_manager.Theme
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SettingsViewModel
    }

    suspend fun getTheme(): Theme = withContext(Dispatchers.IO) {
        return@withContext preferencesManager.themePreferencesFlow.first().theme
    }

    fun updateTheme(theme: Theme) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.updateTheme(theme)
        }
    }
}