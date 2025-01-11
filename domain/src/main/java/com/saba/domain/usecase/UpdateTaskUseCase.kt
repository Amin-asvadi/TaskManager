package com.saba.domain.usecase

import com.saba.data.model.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(task: TaskEntity) {
        taskRepository.updateTask(task)
    }
}
