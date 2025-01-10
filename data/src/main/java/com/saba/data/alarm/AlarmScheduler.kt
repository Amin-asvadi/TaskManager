package com.saba.data.alarm

import com.saba.data.local.TaskEntity

interface AlarmScheduler {
    fun schedule(item: TaskEntity)
    fun cancel(item: TaskEntity)
}