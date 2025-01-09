package com.saba.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {
    var observableLoginStatus: Flow<Boolean>
    var tokenCode: String?


    fun clearData()
    fun signOut()
}