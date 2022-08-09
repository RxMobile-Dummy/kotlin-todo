package com.remindme.category_task.di

import com.remindme.category_task.mapper.CategoryMapper
import com.remindme.category_task.presentation.bottomsheet.CategoryAddViewModel
import com.remindme.category_task.presentation.bottomsheet.CategoryEditViewModel
import com.remindme.category_task.presentation.list.CategoryListViewModelImpl
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.domain.usecase.category.AddCategory
import com.remindme.domain.usecase.category.DeleteCategory
import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.category.UpdateCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {

    @Provides
    fun provideCategoryListViewModelImpl(
        loadAllCategories: LoadAllCategories,
        categoryMapper: CategoryMapper
    ): CategoryListViewModel {
        return CategoryListViewModelImpl(loadAllCategories, categoryMapper)
    }

    @Provides
    fun getCategoryAddViewModel(
        addCategory: AddCategory,
        categoryMapper: CategoryMapper
    ): CategoryAddViewModel {
        return CategoryAddViewModel(addCategory, categoryMapper)
    }

    @Provides
    fun getCategoryEditViewModel(
        updateCategory: UpdateCategory,
        deleteCategory: DeleteCategory,
        categoryMapper: CategoryMapper
    ): CategoryEditViewModel {
        return CategoryEditViewModel(updateCategory, deleteCategory,categoryMapper)
    }

    @Provides
    fun categoryMapper(
    ): CategoryMapper {
        return CategoryMapper()
    }
}