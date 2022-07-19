package com.remindme.task.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.task.mapper.TaskWithCategoryMapper
import com.remindme.task.model.TaskWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible to handle the interaction between the presentation and business logic from
 * Task Section.
 */

class TaskListViewModel @Inject constructor(
    private val loadAllTasksUseCase: LoadUncompletedTasks,
    private val updateTaskStatusUseCase: UpdateTaskStatus,
    private val taskWithCategoryMapper: TaskWithCategoryMapper
) : ViewModel() {

    fun loadTaskList(categoryId: Long? = null): Flow<TaskListViewState> = flow {
        loadAllTasksUseCase(categoryId = categoryId)
            .map { task -> taskWithCategoryMapper.toView(task) }
            .catch { error -> emit(TaskListViewState.Error(error)) }
            .collect { tasks ->
                val state = if (tasks.isNotEmpty()) {
                    TaskListViewState.Loaded(tasks)
                } else {
                    TaskListViewState.Empty
                }
                emit(state)
            }
    }

    fun updateTaskStatus(item: TaskWithCategory) = viewModelScope.launch {
        updateTaskStatusUseCase(item.task.id)
    }
}
