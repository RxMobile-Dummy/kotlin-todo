package com.remindme.domain.usecase.task.implementation

import android.content.Context
import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.usecase.task.LoadTask
import com.remindme.domain.usecase.task.UpdateTask
import com.remindme.domain.usecase.task.UpdateTaskTitle
import javax.inject.Inject

class UpdateTaskTitleImpl @Inject constructor(
    private val loadTask: LoadTask,
    private val updateTask: UpdateTask,
    private val glanceInteractor: GlanceInteractor
) : UpdateTaskTitle {

    override suspend fun invoke(taskId: Long?, title: String) {
        val task = loadTask(taskId) ?: return
        val updatedTask = task.copy(title = title)
        updateTask(updatedTask)
        glanceInteractor.onTaskListUpdated()
    }
}
