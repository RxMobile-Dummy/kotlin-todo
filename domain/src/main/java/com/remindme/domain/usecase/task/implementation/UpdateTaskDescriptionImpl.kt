package com.remindme.domain.usecase.task.implementation

import android.content.Context
import com.remindme.domain.usecase.task.LoadTask
import com.remindme.domain.usecase.task.UpdateTask
import com.remindme.domain.usecase.task.UpdateTaskDescription
import javax.inject.Inject

class UpdateTaskDescriptionImpl @Inject constructor(
    private val loadTask: LoadTask,
    private val updateTask: UpdateTask
) : UpdateTaskDescription {

    override suspend fun invoke(taskId: Long?, description: String) {
        val task = loadTask(taskId) ?: return
        val updatedTask = task.copy(description = description)
        updateTask(updatedTask)
    }
}
