package com.remindme.domain.usecase.category.implementation

import com.remindme.domain.model.Category
import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.usecase.category.DeleteCategory
import javax.inject.Inject

class DeleteCategoryImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : DeleteCategory {

    override suspend operator fun invoke(category: Category) =
        categoryRepository.deleteCategory(category)
}
