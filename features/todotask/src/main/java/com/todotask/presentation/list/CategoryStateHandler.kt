package com.todotask.presentation.list

import com.remindme.categoryapi.presentation.CategoryState
import com.todotask.presentation.detail.main.CategoryId
import javax.inject.Inject

data class CategoryStateHandler @Inject constructor(
    val state: CategoryState = CategoryState.Empty,
    val currentCategory: CategoryId? = null,
    val onCategoryChange: (CategoryId?) -> Unit = {},
)
