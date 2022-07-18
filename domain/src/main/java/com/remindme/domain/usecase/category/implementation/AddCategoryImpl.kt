package com.remindme.domain.usecase.category.implementation

import com.remindme.domain.model.Category
import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.usecase.category.AddCategory
import mu.KLogging
import javax.inject.Inject

class AddCategoryImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : AddCategory {

    override suspend operator fun invoke(category: Category) {
        if (category.name.isBlank()) {
            logger.debug { "Category cannot be inserted with a empty name" }
            return
        }
        categoryRepository.insertCategory(category)
    }

    companion object : KLogging()
}
