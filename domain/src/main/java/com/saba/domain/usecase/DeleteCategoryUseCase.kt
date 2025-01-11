package com.saba.domain.usecase

import com.saba.data.model.local.CategoryEntity
import com.saba.data.repository.local.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(category: CategoryEntity) {
        categoryRepository.deleteCategory(category)
    }
}
