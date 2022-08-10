package com.remindme.searchTask.presentation

import com.remindme.searchTask.model.TaskSearchItem
import com.todotask.model.TaskWithCategory
import com.todotask.presentation.list.TaskListViewState

data class SearchStateHandler(
    val state: SearchViewState = SearchViewState.Empty,
    val onCheckedChange: (TaskWithCategory) -> Unit = {},
    val onItemClick: (Long?) -> Unit = {},
    val onAddClick: () -> Unit = {},
    )
