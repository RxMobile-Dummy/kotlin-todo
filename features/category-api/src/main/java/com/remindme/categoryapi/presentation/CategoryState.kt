package com.remindme.categoryapi.presentation

import com.remindme.categoryapi.model.Category

/**
 * Represents the states of [CategoryListViewModel].
 */
sealed class CategoryState {

    /**
     * Loading state.
     */
    object Loading : CategoryState()

    /**
     * Loaded state.
     *
     * @property categoryList the loaded category list
     */
    data class Loaded(val categoryList: List<Category>) : CategoryState()

    /**
     * Empty state, there are no categories to be shown.
     */
    object Empty : CategoryState()
}
