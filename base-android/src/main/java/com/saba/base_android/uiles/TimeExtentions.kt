package com.saba.base_android.uiles

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime?.toFormattedString(pattern: String = "yyyy/MM/dd HH:mm"): String? {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this?.format(formatter)
}

fun String.toLocalDateTime(pattern: String = "yyyy/MM/dd HH:mm"): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(this, formatter)
}
fun String.reminderToLocalDateTime(): LocalDateTime? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        LocalDateTime.parse(this, formatter)
    } catch (e: Exception) {
        null
    }
}
fun convertToLocalDateTime(date: String?, time: String?): LocalDateTime? {
    if (date.isNullOrEmpty()|| time.isNullOrEmpty()) return null
    val dateTimeString = "$date $time"
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
    return LocalDateTime.parse(dateTimeString, formatter)
}

fun String.toLocalDateTimeWithTime(time: String): LocalDateTime {
    val dateTimeString = "$this $time"
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
    return LocalDateTime.parse(dateTimeString, formatter)
}
fun LocalDateTime.isBeforeNow(): Boolean {
    return this.isBefore(LocalDateTime.now())
}