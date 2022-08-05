package com.remindme.domain.usecase.task.implementation

import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.model.Task
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.task.AddTask
import mu.KLogging
import javax.inject.Inject

class AddTaskImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val glanceInteractor: GlanceInteractor
) : AddTask {

    override suspend operator fun invoke(task: Task
    ) {
        if (task.title.isBlank()) {
            logger.debug { "Task cannot be inserted with a empty title" }
            return
        }

        taskRepository.insertTask(task)
        glanceInteractor.onTaskListUpdated()
    }

    companion object : KLogging()
}
