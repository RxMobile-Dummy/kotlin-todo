package com.remindme.category.presentation.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.category.mapper.CategoryMapper
import com.remindme.categoryapi.model.Category
import com.remindme.domain.usecase.category.DeleteCategory
import com.remindme.domain.usecase.category.UpdateCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryEditViewModel @Inject constructor(
    private val updateCategoryUseCase: UpdateCategory,
    private val deleteCategoryUseCase: DeleteCategory,
    private val mapper: CategoryMapper
) : ViewModel() {

    fun updateCategory(category: Category) {
        if (category.name.isEmpty()) return

        viewModelScope.launch {
            val domainCategory = mapper.toDomain(category)
            updateCategoryUseCase(domainCategory)
        }
    }

    fun deleteCategory(category: Category) = viewModelScope.launch {
        val domainCategory = mapper.toDomain(category)
        deleteCategoryUseCase(domainCategory)
    }
}
