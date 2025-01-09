package com.saba.domain.usecase

import com.saba.data.local.CategoryEntity
import com.saba.data.local.TaskEntity
import com.saba.data.repository.local.CategoryRepository
import com.saba.data.repository.local.TaskRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(category: CategoryEntity) {
        categoryRepository.updateCategory(category)
    }
}
