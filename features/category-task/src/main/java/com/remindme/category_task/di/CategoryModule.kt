package com.remindme.category.di

import com.remindme.category.mapper.CategoryMapper
import com.remindme.category.presentation.bottomsheet.CategoryAddViewModel
import com.remindme.category.presentation.bottomsheet.CategoryEditViewModel
import com.remindme.category.presentation.list.CategoryListViewModelImpl
import com.remindme.categoryapi.presentation.CategoryListViewModel
import com.remindme.domain.usecase.category.AddCategory
import com.remindme.domain.usecase.category.DeleteCategory
import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.category.UpdateCategory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module

///**
// * Category dependency injection module.
// */
//val categoryModule = module {
//    viewModel<CategoryListViewModel> { CategoryListViewModelImpl(get(), get()) }
//    viewModel { CategoryAddViewModel(get(), get()) }
//    viewModel { CategoryEditViewModel(get(), get(), get()) }
//
//    // Mapper
//    factory { CategoryMapper() }
//}
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
        categoryMapper:CategoryMapper
    ): CategoryEditViewModel {
        return CategoryEditViewModel(updateCategory, deleteCategory,categoryMapper)
    }

    @Provides
    fun categoryMapper(
    ): CategoryMapper {
        return CategoryMapper()
    }
}