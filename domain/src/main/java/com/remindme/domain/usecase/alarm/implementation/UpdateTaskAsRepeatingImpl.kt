package com.remindme.domain.usecase.alarm.implementation

import com.remindme.domain.model.AlarmInterval
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.alarm.UpdateTaskAsRepeating
import mu.KLogging
import javax.inject.Inject

class UpdateTaskAsRepeatingImpl @Inject constructor(
    private val taskRepository: TaskRepository
) : UpdateTaskAsRepeating {

    override suspend operator fun invoke(taskId: Long?, interval: AlarmInterval?) {
        val task = taskRepository.findTaskById(taskId) ?: return
        logger.debug("UpdateTaskAsRepeating = Task = '${task.title} as '$interval")

        val updatedTask = if (interval == null) {
            task.copy(alarmInterval = null, isRepeating = false)
        } else {
            task.copy(alarmInterval = interval, isRepeating = true)
        }

        taskRepository.updateTask(updatedTask)
    }

    companion object : KLogging()
}
