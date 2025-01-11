package com.saba.data.api

import com.saba.data.model.RemoteModelItem
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET("posts")
    suspend fun remoteApi(
    ): Response<List<RemoteModelItem>>

}