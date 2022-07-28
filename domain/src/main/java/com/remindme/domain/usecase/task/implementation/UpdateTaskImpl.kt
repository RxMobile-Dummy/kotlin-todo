package com.remindme.domain.usecase.task.implementation

import android.content.Context
import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.model.Task
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.task.UpdateTask
import javax.inject.Inject

/**
 * Use case to update a task from the database.
 */
class UpdateTaskImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val glanceInteractor: GlanceInteractor
) : UpdateTask {

    override suspend operator fun invoke(task: Task) {
        taskRepository.updateTask(task)
        glanceInteractor.onTaskListUpdated()
    }
}
