package com.saba.alarmmanager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.saba.base_android.uiles.Constant
import com.saba.base_android.uiles.TimeUtil
import com.saba.data.model.local.CategoryEntity
import com.saba.data.repository.local.DataStoreRepository
import com.saba.data.worker.TaskWorker
import com.saba.domain.usecase.AddCategoryUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class TaskApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var addCategoryUseCase: AddCategoryUseCase

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workManager: WorkManager
    override fun onCreate() {
        super.onCreate()
        initDefaultDatabaseTable()
        initWorkManager()
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun initWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            TaskWorker::class.java,
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setInitialDelay(TimeUtil.getInitialDelay(), TimeUnit.MILLISECONDS)
            .addTag(Constant.UNIQUE_WORK_NAME)
            .build()

        workManager.enqueueUniquePeriodicWork(
            Constant.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest
        )
    }
    private fun initDefaultDatabaseTable() {
        val defaultCategories = listOf(
            CategoryEntity(category = "همه"),
            CategoryEntity(category = "کارهای روزانه"),
            CategoryEntity(category = "کارهای فوری"),
            CategoryEntity(category = "کارهای هفتگی"),
            CategoryEntity(category = "پروژه های کاری"),
            CategoryEntity(category = "اهداف شخصی"),
            CategoryEntity(category = "خانواده و دوستان"),
            CategoryEntity(category = "مالی و حسابداری"),
            CategoryEntity(category = "ورزش و سلامتی"),
            CategoryEntity(category = "یادگیری و آموزش"),
            CategoryEntity(category = "خلاقیت و سرگرمی")
        )
        CoroutineScope(Dispatchers.IO).launch {

            val deferredTasks = defaultCategories.map { category ->
                async {
                    addCategoryUseCase.execute(category)
                }
            }
            deferredTasks.awaitAll()
            dataStoreRepository.addDefaultCategory(true)

        }
    }
}
