package com.remindme.task.presentation.list

import com.remindme.categoryapi.presentation.CategoryState
import com.remindme.task.presentation.detail.main.CategoryId
import javax.inject.Inject

internal data class CategoryStateHandler @Inject constructor(
    val state: CategoryState = CategoryState.Empty,
    val currentCategory: CategoryId? = null,
    val onCategoryChange: (CategoryId?) -> Unit = {},
)
