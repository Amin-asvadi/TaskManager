package com.saba.domain.usecase

import com.saba.data.alarm.AlarmScheduler
import com.saba.data.model.local.TaskEntity
import javax.inject.Inject

class CancellAlarmSchedulerUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    suspend fun execute(item: TaskEntity) {
        alarmScheduler.cancel(item)
    }
}