package com.saba.data.validation

import androidx.core.text.isDigitsOnly
import com.saba.base_android.network.NetworkErrorData
import com.saba.base_android.uiles.isBeforeNow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ValidationTask {

    fun execute(date:String?,time:String?,dateAndTime:String): NetworkErrorData {
        if (date.isNullOrEmpty()) {
            return NetworkErrorData(
                isError = true,
                text = "تاریخ یاداوری وارد نشده است"
            )
        }
        if (time.isNullOrEmpty()) {
            return NetworkErrorData(
                isError = true,
                text = "زمان یاداوری وارد نشده است"
            )
        }
        if (dateAndTime.isNotEmpty()){
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            val inputDateTime = LocalDateTime.parse(dateAndTime,formatter)

            if (inputDateTime.isBeforeNow()) {
                return NetworkErrorData(
                    isError = true,
                    text = "زمان یاداوری قبل امروز است"
                )
            } else {
                NetworkErrorData(isError = false, text = "")
            }
        }

        return NetworkErrorData(isError = false, text = "")
    }
}
