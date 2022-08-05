package com.remindme.searchTask.di

import com.remindme.domain.usecase.search.SearchTasksByName
import com.remindme.searchTask.mapper.TaskSearchMapper
import com.remindme.searchTask.presentation.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
@Module
@InstallIn(SingletonComponent::class)
class SearchModule {

    @Provides
    fun getSearchViewModel(
        searchTasksByName: SearchTasksByName,
        taskSearchMapper: TaskSearchMapper
    ): SearchViewModel {
        return SearchViewModel(searchTasksByName, taskSearchMapper)
    }


    @Provides
    fun taskSearchMapper(
    ): TaskSearchMapper {
        return TaskSearchMapper()
    }
}