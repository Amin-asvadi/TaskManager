package com.saba.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.saba.data.model.local.toEntityList
import com.saba.data.repository.local.TaskRepository
import com.saba.data.repository.remote.RemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class TaskWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val remoteRepository: RemoteRepository,
    private val taskRepository: TaskRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        lateinit var result: Result
        runCatching {
           val remote = remoteRepository.getRemoteList().toEntityList()
            remote.forEach {
                taskRepository.upsetRemote(it)
                delay(10)
            }
        }.onSuccess {
            result = Result.success()
        }.onFailure {
            result = Result.retry()
        }
        return result
    }


}