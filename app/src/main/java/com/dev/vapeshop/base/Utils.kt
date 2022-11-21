package com.dev.vapeshop.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dev.vapeshop.features.preferences_manager.Theme
import kotlin.reflect.KClass

fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
    //Get default constructor
    val constructor = clazz.constructors.first()

    //Map constructor parameters to map values
    val args = constructor
        .parameters.associateWith { map[it.name] }

    //return object from constructor call
    return constructor.callBy(args)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

fun String.toThemeEnum(): Theme {
    return try {
        Theme.valueOf(this)
    } catch (e: Exception) {
        Theme.THEME_SYSTEM
    }
}