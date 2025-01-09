package com.saba.data.repository.local

import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(): Flow<List<CategoryEntity>>
    suspend fun getCategoryById(categoryId: Int): CategoryEntity?
    suspend fun addCategory(category: CategoryEntity)
    suspend fun updateCategory(category:  CategoryEntity)
    suspend fun deleteCategory(category:  CategoryEntity)
}