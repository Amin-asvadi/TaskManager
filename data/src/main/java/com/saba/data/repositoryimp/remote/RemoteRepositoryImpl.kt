package com.saba.data.repositoryimp.remote

import com.saba.base_android.network.bodyOrThrow
import com.saba.data.api.RemoteApi
import com.saba.data.model.RemoteModelItem
import com.saba.data.repository.remote.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val api: RemoteApi
) : RemoteRepository {
    override suspend fun getRemoteList(): List<RemoteModelItem> {
        return api.remoteApi().bodyOrThrow()
    }

}
