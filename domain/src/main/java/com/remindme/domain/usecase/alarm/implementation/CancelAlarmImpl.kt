package com.remindme.domain.usecase.alarm.implementation

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.alarm.CancelAlarm
import javax.inject.Inject

 class CancelAlarmImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmInteractor: AlarmInteractor
) : CancelAlarm {


    override suspend fun invoke(taskId: Long?) {
        val task = taskRepository.findTaskById(taskId) ?: return

        val updatedTask = task.copy(dueDate = null)

        task.id?.let { alarmInteractor.cancel(it.toLong()) }
        taskRepository.updateTask(updatedTask)
    }
}
