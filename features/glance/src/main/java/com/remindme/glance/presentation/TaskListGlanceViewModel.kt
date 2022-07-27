package com.remindme.glance.presentation

import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.glance.mapper.TaskMapper
import com.remindme.glance.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
@HiltViewModel
class TaskListGlanceViewModel @Inject constructor(
    private val loadAllTasksUseCase: LoadUncompletedTasks,
    private val updateTaskStatus: UpdateTaskStatus,
    private val taskMapper: TaskMapper
): ViewModel() {


    fun loadTaskList(categoryId: Long? = null): Flow<List<Task>> =
        loadAllTasksUseCase(categoryId = categoryId).map { taskMapper.toView(it) }

    suspend fun updateTaskAsCompleted(taskId: Int?) =
        updateTaskStatus(taskId)
}
