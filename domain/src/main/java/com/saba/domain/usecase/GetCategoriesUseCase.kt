package com.saba.domain.usecase

import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.CategoryRepository
import com.saba.data.repository.local.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    fun execute(): Flow<List<CategoryEntity>> {
        return categoryRepository.getAllCategories()
    }
}