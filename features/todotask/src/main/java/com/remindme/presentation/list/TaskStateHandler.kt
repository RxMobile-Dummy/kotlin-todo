package com.remindme.task.presentation.list

import com.remindme.task.model.TaskWithCategory
import javax.inject.Inject

internal data class TaskStateHandler(
    val state: TaskListViewState = TaskListViewState.Empty,
    val onCheckedChange: (TaskWithCategory) -> Unit = {},
    val onItemClick: (Int?) -> Unit = {},
    val onAddClick: () -> Unit = {},
)
