package com.remindme.categoryapi.presentation

import com.remindme.categoryapi.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

/**
 * Represents the states of [CategoryListViewModel].
 */
sealed class CategoryState {

    /**
     * Loading state.
     */
    object Loading : CategoryState(), Flow<CategoryState> {
        override suspend fun collect(collector: FlowCollector<CategoryState>) {
            TODO("Not yet implemented")
        }
    }


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
