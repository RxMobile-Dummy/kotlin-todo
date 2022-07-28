package com.todotask.presentation.detail.main

import com.todotask.model.Task


sealed class TaskDetailState {

    object Loading : TaskDetailState()

    object Error : TaskDetailState()

    data class Loaded(val task: Task) : TaskDetailState()
}
