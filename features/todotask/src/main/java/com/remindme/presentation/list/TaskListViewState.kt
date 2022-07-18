package com.remindme.task.presentation.list

import com.remindme.task.model.TaskWithCategory

/**
 * Presentation entity to represent the view states of Task Section.
 */
sealed class TaskListViewState {

    object Loading : TaskListViewState()

    data class Error(val cause: Throwable) : TaskListViewState()

    data class Loaded(val items: List<TaskWithCategory>) : TaskListViewState()

    object Empty : TaskListViewState()
}
