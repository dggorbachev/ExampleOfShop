package com.dev.vapeshop.features.preferences_manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dev.vapeshop.base.dataStore
import com.dev.vapeshop.base.toThemeEnum
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesManager @Inject constructor(private val context: Context) {

    val themePreferencesFlow =
        context.dataStore.data.map { preferences ->
            val theme =
                (preferences[PreferencesKeys.THEME] ?: Theme.THEME_UNDEFINED.toString()).toThemeEnum()

            ThemePreferences(theme)
        }

    suspend fun updateTheme(theme: Theme) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme.toString()
        }
    }

    private object PreferencesKeys {
        val THEME = stringPreferencesKey("theme")
    }
}