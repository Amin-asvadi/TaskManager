package com.saba.data.repository.local

interface DataStoreRepository {

    suspend fun darkMode(darkMode: Boolean)

}