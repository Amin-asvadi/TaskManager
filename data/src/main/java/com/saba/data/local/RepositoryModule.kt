package com.saba.data.local

import com.saba.data.repository.local.CategoryRepository
import com.saba.data.repository.local.TaskRepository
import com.saba.data.repositoryimp.local.CategoryRepositoryImpl
import com.saba.data.repositoryimp.local.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl:CategoryRepositoryImpl
    ): CategoryRepository
}
