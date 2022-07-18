package com.remindme.categoryapi.presentation

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel responsible to load the available categories.
 */
abstract class CategoryListViewModel : ViewModel() {

    /**
     * Loads the available categories in a flow of states.
     */
    abstract fun loadCategories(): Flow<CategoryState>
}
