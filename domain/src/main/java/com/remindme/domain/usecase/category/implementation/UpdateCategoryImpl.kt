package com.remindme.domain.usecase.category.implementation

import com.remindme.domain.model.Category
import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.usecase.category.UpdateCategory
import javax.inject.Inject

class UpdateCategoryImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : UpdateCategory {

    override suspend operator fun invoke(category: Category) {
        categoryRepository.updateCategory(category)
    }
}
