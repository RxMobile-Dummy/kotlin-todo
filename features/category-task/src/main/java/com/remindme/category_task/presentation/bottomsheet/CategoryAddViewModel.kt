package com.remindme.category_task.presentation.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.category_task.mapper.CategoryMapper
import com.remindme.categoryapi.model.Category
import com.remindme.domain.usecase.category.AddCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategory,
    private val categoryMapper: CategoryMapper
) : ViewModel() {

    fun addCategory(category: Category) {
        if (category.name.isEmpty()) return

        viewModelScope.launch {
            val domainCategory = categoryMapper.toDomain(category)
            addCategoryUseCase(domainCategory)
        }
    }
}
