package com.remindme.alarm.interactor

import com.remindme.alarm.mapper.TaskMapper
import com.remindme.alarm.notification.TaskNotification
import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.model.Task
import logcat.logcat
import javax.inject.Inject

internal class NotificationInteractorImpl @Inject constructor(
    private val taskNotification: TaskNotification,
    private val taskMapper: TaskMapper
) : NotificationInteractor {

    override fun show(task: Task) {
        logcat { "show - alarmId = ${task.id}" }
        if (task.isRepeating) {
            taskNotification.showRepeating(taskMapper.fromDomain(task))
        } else {
            taskNotification.show(taskMapper.fromDomain(task))
        }
    }

    override fun dismiss(notificationId: Long) {
        logcat { "dismiss - alarmId = $notificationId" }
        taskNotification.dismiss(notificationId)
    }
}
