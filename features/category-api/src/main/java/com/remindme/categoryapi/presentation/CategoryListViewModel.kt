package com.remindme.categoryapi.presentation

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.contracts.Returns

/**
 * ViewModel responsible to load the available categories.
 */

@HiltViewModel
open class CategoryListViewModel @Inject constructor(): ViewModel() {

    /**
     * Loads the available categories in a flow of states.
     */
    open fun loadCategories(): Flow<CategoryState> = flow{
    }

}
