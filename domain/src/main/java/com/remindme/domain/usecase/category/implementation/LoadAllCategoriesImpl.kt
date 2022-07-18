package com.remindme.domain.usecase.category.implementation

import com.remindme.domain.model.Category
import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.usecase.category.LoadAllCategories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAllCategoriesImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
) : LoadAllCategories {

    override operator fun invoke(): Flow<List<Category>> =
        categoryRepository.findAllCategories()
}
