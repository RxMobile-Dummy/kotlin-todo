package com.remindme.domain.usecase.alarm.implementation

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.alarm.ScheduleAlarm
import java.util.Calendar
import javax.inject.Inject

class ScheduleAlarmImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmInteractor: AlarmInteractor
) : ScheduleAlarm {

    /**
     * Schedules a new alarm.
     *
     * @param taskId the alarm id
     * @param calendar the time to the alarm be scheduled
     */
    override suspend operator fun invoke(taskId: Long?, calendar: Calendar) {
        val task = taskRepository.findTaskById(taskId) ?: return
        val updatedTask = task.copy(dueDate = calendar)
        taskRepository.updateTask(updatedTask)

        taskId?.let { it.toLong().let { it1 -> alarmInteractor.schedule(it1, calendar.time.time) } }
    }
}
