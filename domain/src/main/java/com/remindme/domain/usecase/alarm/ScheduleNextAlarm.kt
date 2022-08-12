package com.remindme.domain.usecase.alarm

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.model.AlarmInterval
import com.remindme.domain.model.AlarmInterval.DAILY
import com.remindme.domain.model.AlarmInterval.HOURLY
import com.remindme.domain.model.AlarmInterval.MONTHLY
import com.remindme.domain.model.AlarmInterval.WEEKLY
import com.remindme.domain.model.AlarmInterval.YEARLY
import com.remindme.domain.model.Task
import com.remindme.domain.provider.CalendarProvider
import com.remindme.domain.repository.TaskRepository
import mu.KLogging
import java.util.Calendar
import javax.inject.Inject

/**
 * Schedules the next alarm entry or the missing ones in a repeating alarm.
 */
class ScheduleNextAlarm @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmInteractor: AlarmInteractor,
    private val calendarProvider: CalendarProvider
) {

    /**
     * Schedules the next alarm.
     *
     * @param task task to be rescheduled
     */
    suspend operator fun invoke(task: Task) {
        require(task.isRepeating) { "Task is not repeating" }
        require(task.dueDate != null) { "Task has no due date" }
        require(task.alarmInterval != null) { "Task has no alarm interval" }

        val currentTime = calendarProvider.getCurrentCalendar()
        do {
            updatedAlarmTime(task.dueDate, task.alarmInterval)
        } while (currentTime.after(task.dueDate))

        taskRepository.updateTask(task)
        task.id?.let { alarmInteractor.schedule(it.toLong(), task.dueDate.time.time,false) }
        logger.debug { "ScheduleNextAlarm = Task = '${task.title}' at ${task.dueDate.time} " }
    }

    private fun updatedAlarmTime(calendar: Calendar, alarmInterval: AlarmInterval) =
        when (alarmInterval) {
            HOURLY -> calendar.apply { add(Calendar.HOUR, 1) }
            DAILY -> calendar.apply { add(Calendar.DAY_OF_MONTH, 1) }
            WEEKLY -> calendar.apply { add(Calendar.WEEK_OF_MONTH, 1) }
            MONTHLY -> calendar.apply { add(Calendar.MONTH, 1) }
            YEARLY -> calendar.apply { add(Calendar.YEAR, 1) }
        }

    companion object : KLogging()
}
