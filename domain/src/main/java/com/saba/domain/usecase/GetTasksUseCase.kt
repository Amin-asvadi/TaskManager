package com.saba.domain.usecase

import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    fun execute(): Flow<List<TaskEntity>> {
        return taskRepository.getAllTasks()
    }
}