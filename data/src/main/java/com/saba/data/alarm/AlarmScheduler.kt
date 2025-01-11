package com.saba.data.alarm

import com.saba.data.model.local.TaskEntity

interface AlarmScheduler {
    fun schedule(item: TaskEntity)
    fun cancel(item: TaskEntity)
}