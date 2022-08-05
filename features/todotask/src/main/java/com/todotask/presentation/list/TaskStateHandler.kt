package com.todotask.presentation.list

import com.todotask.model.TaskWithCategory
import com.todotask.presentation.list.TaskListViewState
import javax.inject.Inject

internal data class TaskStateHandler(
    val state: TaskListViewState = TaskListViewState.Empty,
    val onCheckedChange: (TaskWithCategory) -> Unit = {},
    val onItemClick: (Long?) -> Unit = {},
    val onAddClick: () -> Unit = {},
)
