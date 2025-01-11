package com.saba.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_key")
    val DEFAULT_CATEGORY_KEY = booleanPreferencesKey("default_category_key")

}