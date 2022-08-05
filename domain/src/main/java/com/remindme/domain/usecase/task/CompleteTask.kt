package com.remindme.domain.usecase.task

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.model.Task
import com.remindme.domain.provider.CalendarProvider
import com.remindme.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Use case to set a task as completed in the database.
 */
class CompleteTask @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmInteractor: AlarmInteractor,
    private val notificationInteractor: NotificationInteractor,
    private val calendarProvider: CalendarProvider
) {
    companion object instance {

    }

    /**
     * Completes the given task.
     *
     * @param taskId the task id
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(taskId: Long?) {
        val task = taskRepository.findTaskById(taskId) ?: return
        invoke(task)
    }

    /**
     * Completes the given task.
     *
     * @param task the task to be updated
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(task: Task) {
        val updatedTask = updateTaskAsCompleted(task)
        taskRepository.updateTask(updatedTask)
        task.id.let { alarmInteractor.cancel(it) }
        task.id.let { notificationInteractor.dismiss(it) }
    }

    private fun updateTaskAsCompleted(task: Task) =
        task.copy(completed = true, completedDate = calendarProvider.getCurrentCalendar())
}
