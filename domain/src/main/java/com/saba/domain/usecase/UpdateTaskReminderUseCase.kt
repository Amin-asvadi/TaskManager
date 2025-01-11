package com.saba.domain.usecase

import com.saba.data.repository.local.TaskRepository
import javax.inject.Inject

class UpdateTaskReminderUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(taskId: Int, reminder: Boolean) {
        taskRepository.updateReminder(taskId = taskId, isReminderEnabled = reminder)
    }
}
