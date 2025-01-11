package com.saba.data.repositoryimp.local

import com.saba.data.model.local.CategoryEntity
import com.saba.data.local.DatabaseDao
import com.saba.data.repository.local.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val databaseDao: DatabaseDao
) : CategoryRepository {
    override fun getAllCategories(): Flow<List<CategoryEntity>> {
        return databaseDao.getAllCategories()
    }

    override suspend fun getCategoryById(categoryId: Int): CategoryEntity? {
        return databaseDao.getCategoryById(categoryId)
    }

    override suspend fun addCategory(category: CategoryEntity) {
        databaseDao.insertCategory(category)
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        databaseDao.updateCategory(category)
    }

    override suspend fun deleteCategory(category: CategoryEntity) {
        databaseDao.deleteCategory(category)
    }

}
