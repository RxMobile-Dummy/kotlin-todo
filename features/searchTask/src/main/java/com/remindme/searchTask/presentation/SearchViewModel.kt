package com.remindme.search.presentation

import androidx.lifecycle.ViewModel
import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.usecase.search.SearchTasksByName
import com.remindme.search.mapper.TaskSearchMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val findTaskUseCase: SearchTasksByName,
    private val mapper: TaskSearchMapper
) : ViewModel() {

    fun findTasksByName(name: String = ""): Flow<SearchViewState> = flow {
        findTaskUseCase(name).collect { taskList ->
            val state = if (taskList.isNotEmpty()) {
                onListLoaded(taskList)
            } else {
                SearchViewState.Empty
            }
            emit(state)
        }
    }

    private fun onListLoaded(taskList: List<TaskWithCategory>): SearchViewState {
        val searchList = mapper.toTaskSearch(taskList)
        return SearchViewState.Loaded(searchList)
    }
}
