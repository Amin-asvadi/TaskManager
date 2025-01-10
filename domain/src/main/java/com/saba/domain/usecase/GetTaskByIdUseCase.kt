package com.saba.domain.usecase

import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(id:Int): Flow<TaskEntity?> {
        return taskRepository.getTaskById(id)
    }
}