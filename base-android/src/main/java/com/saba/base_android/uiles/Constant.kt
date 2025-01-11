package com.saba.base_android.uiles


object Constant {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    const val DB_NAME = "app_db"
    const val DS_NAME = "app_dS.preferences_pb"
    const val ALARM_TRIGGER = "AlarmTrigger"
    const val MORNING_UPDATE_TIME = 5
    const val UNIQUE_WORK_NAME = "AppPeriodicWork"
    enum class ERROR_MODE {
        TEXTFILED,
        SERVER_ERROR,
        UNDIFINDE,
        SUCCESS,
    }
}