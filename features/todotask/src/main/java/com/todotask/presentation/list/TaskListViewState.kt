package com.todotask.presentation.list

import com.todotask.model.TaskWithCategory

/**
 * Presentation entity to represent the view states of Task Section.
 */
sealed class TaskListViewState {

    object Loading : TaskListViewState()

    data class Error(val cause: Throwable) : TaskListViewState()

    data class Loaded(val items: List<TaskWithCategory>) : TaskListViewState()

    object Empty : TaskListViewState()
}
