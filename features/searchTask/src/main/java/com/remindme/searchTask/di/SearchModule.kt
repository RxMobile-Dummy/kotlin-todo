package com.remindme.search.di

import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.search.SearchTasksByName
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.search.mapper.TaskSearchMapper
import com.remindme.search.presentation.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module

/**
 * Search dependency injection module.
 */
//val searchModule = module {
//
//    // Presentation
//    viewModel { SearchViewModel(get(), get()) }
//
//    // Mappers
//    factory { TaskSearchMapper() }
//}
//@Module
//@InstallIn(SingletonComponent::class)
//object SearchModule {
//
//    @Provides
//    fun getSearchViewModel(
//        searchTasksByName: SearchTasksByName,
//        taskSearchMapper: TaskSearchMapper
//    ): SearchViewModel {
//        return SearchViewModel(searchTasksByName, taskSearchMapper)
//    }
//
//
//    @Provides
//    fun taskSearchMapper(
//    ): TaskSearchMapper {
//        return TaskSearchMapper()
//    }
//}