package com.remindme.category_task.presentation.list

import com.remindme.category_task.mapper.CategoryMapper
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.domain.usecase.category.LoadAllCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryListViewModelImpl @Inject constructor(
    private val loadAllCategories: LoadAllCategories,
    private val categoryMapper: CategoryMapper
) : CategoryListViewModel() {

    override fun loadCategories(): Flow<CategoryState> = flow {
        loadAllCategories().collect { categoryList ->
            if (categoryList.isNotEmpty()) {
                val mappedList = categoryMapper.toView(categoryList)
                emit(CategoryState.Loaded(mappedList))
            } else {
                emit(CategoryState.Empty)
            }
        }
    }


}
