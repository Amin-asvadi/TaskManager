package com.saba.domain.usecase

import com.saba.data.alarm.AlarmScheduler
import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.CategoryRepository
import javax.inject.Inject

class CancellAlarmSchedulerUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    suspend fun execute(item: TaskEntity) {
        alarmScheduler.cancel(item)
    }
}