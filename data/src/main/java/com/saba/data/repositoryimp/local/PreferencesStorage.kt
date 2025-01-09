package com.saba.data.repositoryimp.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.saba.data.preferences.PreferencesKeys
import com.saba.data.repository.local.DataStoreRepository
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


}