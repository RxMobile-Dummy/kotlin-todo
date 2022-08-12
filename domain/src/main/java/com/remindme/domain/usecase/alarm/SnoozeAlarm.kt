package com.remindme.domain.usecase.alarm

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.provider.CalendarProvider
import mu.KLogging
import java.util.Calendar
import javax.inject.Inject

/**
 * Use case to snooze a task from the database.
 */
class SnoozeAlarm @Inject constructor(
    private val calendarProvider: CalendarProvider,
    private val notificationInteractor: NotificationInteractor,
    private val alarmInteractor: AlarmInteractor
) {

    /**
     * Snoozes the task.
     *
     * @param taskId the task id
     * @param minutes time to be snoozed in minutes
     *
     * @return observable to be subscribe
     */
    operator fun invoke(taskId: Long, minutes: Int = DEFAULT_SNOOZE) {
        require(minutes > 0) { "The delay minutes must be positive" }

        val snoozedTime = getSnoozedTask(calendarProvider.getCurrentCalendar(), minutes)
        alarmInteractor.schedule(taskId, snoozedTime,false)
        notificationInteractor.dismiss(taskId)
        logger.debug { "Task snoozed in $minutes minutes" }
    }

    private fun getSnoozedTask(calendar: Calendar, minutes: Int): Long {
        val updatedCalendar = calendar.apply { add(Calendar.MINUTE, minutes) }
        return updatedCalendar.time.time
    }

    companion object : KLogging() {

        private const val DEFAULT_SNOOZE = 15
    }
}
