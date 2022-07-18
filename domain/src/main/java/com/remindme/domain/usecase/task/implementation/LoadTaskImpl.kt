package com.remindme.domain.usecase.task.implementation

import com.remindme.domain.model.Task
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.task.LoadTask
import javax.inject.Inject

class LoadTaskImpl @Inject constructor(private val taskRepository: TaskRepository) : LoadTask {

    override suspend operator fun invoke(taskId: Int?): Task? =
        taskRepository.findTaskById(taskId)
}
