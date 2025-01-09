package com.saba.base_android.network

import android.util.Log

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.bodyOrThrow(): T {
    if (!isSuccessful) {
        val serverException = errorBody()?.string()?.let { errorString ->
            parseHttpException(errorString, code())
        }
        throw serverException ?: HttpException(this)
    }
    return body()!!
}

fun parseHttpException(errorString: String, errorCode: Int): ServerException? {
    return try {
        ServerException("", errorCode, null)
    } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
        Log.d("Error String " ,errorString)
        null

    }
}