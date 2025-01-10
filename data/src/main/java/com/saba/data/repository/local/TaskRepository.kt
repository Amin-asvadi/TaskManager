package com.saba.data.repository.local

import com.saba.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(category:String?): Flow<List<TaskEntity>>
    suspend fun getTaskById(taskId: Int): Flow<TaskEntity?>
    suspend fun addTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
}