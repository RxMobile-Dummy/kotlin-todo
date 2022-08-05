package com.remindme.domain.usecase.alarm

import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.repository.TaskRepository
import mu.KLogging
import javax.inject.Inject

/**
 * Use case to show an alarm.
 */
class ShowAlarm @Inject constructor(
    private val taskRepository: TaskRepository,
    private val notificationInteractor: NotificationInteractor,
    private val scheduleNextAlarm: ScheduleNextAlarm
) {

    /**
     * Shows the alarm.
     *
     * @param taskId the alarm id to be shown
     */
    suspend operator fun invoke(taskId: Long?) {
        val task = taskRepository.findTaskById(taskId) ?: return

        if (task.completed) {
            logger.debug { "Task '${task.title}' is already completed. Will not notify" }
            return
        } else {
            logger.debug { "Notifying task '${task.title}'" }
            notificationInteractor.show(task)
        }

        if (task.isRepeating) {
            scheduleNextAlarm(task)
        }
    }

    companion object : KLogging()
}
