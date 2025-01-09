package com.saba.base_android.network

class ServerException(
    private val serverMessage: String?,
    val code: Int,
    private val metaCode: String?
) : Exception(
    "Server error $code ($metaCode) $serverMessage"
)
