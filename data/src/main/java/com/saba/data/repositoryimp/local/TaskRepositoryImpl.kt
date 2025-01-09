package com.saba.data.repositoryimp.local

import com.saba.data.local.DatabaseDao
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val databaseDao: DatabaseDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return databaseDao.getAllTasks()
    }

    override suspend fun getTaskById(taskId: Int): TaskEntity? {
        return databaseDao.getTaskById(taskId)
    }

    override suspend fun addTask(task: TaskEntity) {
        databaseDao.insertTask(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        databaseDao.updateTask(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        databaseDao.deleteTask(task)
    }
}
