package com.saba.base_android.uiles


object Constant {
    const val BASE_URL = "https://newsapi.org/v2/"
    const val DB_NAME = "app_db"
    const val DS_NAME = "app_dS.preferences_pb"
    enum class ERROR_MODE {
        TEXTFILED,
        SERVER_ERROR,
        UNDIFINDE,
        SUCCESS,
    }
}