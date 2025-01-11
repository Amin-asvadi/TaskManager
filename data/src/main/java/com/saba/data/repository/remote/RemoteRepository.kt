package com.saba.data.repository.remote

import com.saba.data.model.RemoteModelItem

interface RemoteRepository {
    suspend fun getRemoteList(): List<RemoteModelItem>
}