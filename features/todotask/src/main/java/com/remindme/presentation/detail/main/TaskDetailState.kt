package com.remindme.task.presentation.detail.main

import com.remindme.task.model.Task


sealed class TaskDetailState {

    object Loading : TaskDetailState()

    object Error : TaskDetailState()

    data class Loaded(val task: Task) : TaskDetailState()
}
