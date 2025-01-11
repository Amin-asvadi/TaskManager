package com.saba.data.network

import com.saba.data.repository.local.DataStoreRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: DataStoreRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return "AUTH_TOKEN"?.let { authToken ->
            val modifiedRequest = chain.request()
                .newBuilder()
             //   .header("Authorization", authToken)
                .build()
            chain.proceed(modifiedRequest)
        } ?: chain.proceed(chain.request())
    }
}
