package com.saba.alarmmanager

import android.app.Application
import android.content.res.Resources
import android.util.DisplayMetrics
import com.saba.data.local.CategoryEntity
import com.saba.data.repository.local.DataStoreRepository
import com.saba.domain.usecase.AddCategoryUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class TaskApplication : Application() {
    @Inject
    lateinit var addCategoryUseCase: AddCategoryUseCase
    @Inject
    lateinit var dataStoreRepository: DataStoreRepository
    override fun onCreate() {
        super.onCreate()
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
