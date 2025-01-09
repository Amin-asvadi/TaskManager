package com.saba.data.repositoryimp.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.saba.data.preferences.PreferencesKeys
import com.saba.data.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun darkMode(darkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE_KEY] = darkMode
        }
    }

    override suspend fun addDefaultCategory(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DEFAULT_CATEGORY_KEY] = status
        }
    }

    override suspend fun isDefaultCategory(): Boolean {
        val preferencesFlow: Flow<Preferences> = dataStore.data
        var status = false // default value if the key doesn't exist
        preferencesFlow
            .collect { preferences ->
                status = preferences[PreferencesKeys.DEFAULT_CATEGORY_KEY] ?: false
            }
        return status
    }

    override suspend fun isDarkMode(): Boolean {
        val preferencesFlow: Flow<Preferences> = dataStore.data
        var darkMode = false // default value if the key doesn't exist
        preferencesFlow
            .collect { preferences ->
                darkMode = preferences[PreferencesKeys.DARK_MODE_KEY] ?: false
            }
        return darkMode
    }


}