package com.saba.data.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesStorageImpl @Inject constructor(
    prefs: SharedPreferences
) : PreferencesStorage {

    private val loginStatusFlow: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(!tokenCode.isNullOrBlank())
    }

    override var observableLoginStatus: Flow<Boolean>
        get() = loginStatusFlow
        set(_) = throw IllegalAccessException("This property can't be changed")

    override var tokenCode: String? by StringPreference(prefs, TOKEN, "")


    override fun clearData() {
        tokenCode = null
    }

    override fun signOut() {
        tokenCode = ""
    }

    companion object PreferencesKey {
        private const val TOKEN = "1"
        const val PREFS_NAME = "app_pref"

    }
}