package com.saba.data.repositoryimp.local

import com.saba.data.local.DatabaseDao
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val databaseDao: DatabaseDao
) : TaskRepository {

    override fun getAllTasks(category: String?): Flow<List<TaskEntity>> {
        return databaseDao.getAllTasks(category = category)
    }

    override suspend fun getTaskById(taskId: Int): Flow<TaskEntity?> {
        return flow { emit(databaseDao.getTaskById(taskId)) }
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
