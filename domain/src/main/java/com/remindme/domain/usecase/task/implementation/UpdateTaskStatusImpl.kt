package com.remindme.domain.usecase.task.implementation

import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.task.CompleteTask
import com.remindme.domain.usecase.task.UncompleteTask
import com.remindme.domain.usecase.task.UpdateTaskStatus
import javax.inject.Inject

class UpdateTaskStatusImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val glanceInteractor: GlanceInteractor,
    private val completeTask: CompleteTask,
    private val uncompleteTask: UncompleteTask
) : UpdateTaskStatus {

    override suspend operator fun invoke(taskId: Int?) {
        val task = taskRepository.findTaskById(taskId) ?: return
        when (task.completed.not()) {
            true -> completeTask(task)
            false -> uncompleteTask(task)
        }
        glanceInteractor.onTaskListUpdated()
    }
}
