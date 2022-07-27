package com.todotask.presentation.detail.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.core.coroutines.CoroutineDebouncer
import com.remindme.domain.usecase.task.LoadTask
import com.remindme.domain.usecase.task.UpdateTaskCategory
import com.remindme.domain.usecase.task.UpdateTaskDescription
import com.remindme.domain.usecase.task.UpdateTaskTitle
import com.todotask.mapper.TaskMapper
import com.todotask.presentation.detail.main.CategoryId
import com.todotask.presentation.detail.main.TaskId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TaskDetailViewModel @Inject constructor (
    private val loadTaskUseCase: LoadTask,
    private val updateTaskTitle: UpdateTaskTitle,
    private val updateTaskDescription: UpdateTaskDescription,
    private val updateTaskCategory: UpdateTaskCategory,
    private val taskMapper: TaskMapper
) : ViewModel() {

    private val coroutineDebouncer = CoroutineDebouncer()

    fun loadTaskInfo(taskId: TaskId): Flow<TaskDetailState> = flow {
        val task = loadTaskUseCase(taskId = taskId.value)

        if (task != null) {
            val viewTask = taskMapper.toView(task)
            emit(TaskDetailState.Loaded(viewTask))
        } else {
            emit(TaskDetailState.Error)
        }
    }

    fun updateTitle(taskId: TaskId, title: String) =
        coroutineDebouncer(coroutineScope = viewModelScope) {
            updateTaskTitle(taskId.value, title)
        }

    fun updateDescription(taskId: TaskId, description: String) =
        coroutineDebouncer(coroutineScope = viewModelScope) {
            updateTaskDescription(taskId.value, description)
        }

    fun updateCategory(taskId: TaskId, categoryId: CategoryId) =
        viewModelScope.launch {
            updateTaskCategory(taskId = taskId.value, categoryId = categoryId.value)
        }
}
