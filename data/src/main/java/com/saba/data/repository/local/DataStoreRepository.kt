package com.saba.data.repository.local

interface DataStoreRepository {

    suspend fun darkMode(darkMode: Boolean)
    suspend fun addDefaultCategory(status: Boolean)
    suspend fun isDefaultCategory(): Boolean
      fun isDarkMode(): Boolean


}