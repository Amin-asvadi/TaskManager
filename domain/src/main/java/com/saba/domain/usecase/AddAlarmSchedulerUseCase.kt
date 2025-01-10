package com.saba.domain.usecase

import com.saba.data.alarm.AlarmScheduler
import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.CategoryRepository
import javax.inject.Inject

class AddAlarmSchedulerUseCase @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) {
    suspend fun execute(category: TaskEntity) {
        alarmScheduler.schedule(category)
    }
}