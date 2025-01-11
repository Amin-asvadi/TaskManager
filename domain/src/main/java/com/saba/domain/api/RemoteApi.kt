package com.saba.domain.api

import com.saba.data.model.RemoteModelItem
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {
    @GET("auth/provider/app/otp/login")
    suspend fun remoteApi(
    ): Response<List<RemoteModelItem>>

}