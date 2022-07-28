package com.remindme.alarm.interactor

import com.remindme.alarm.notification.TaskNotificationScheduler
import com.remindme.domain.interactor.AlarmInteractor
import logcat.logcat
import javax.inject.Inject

 class AlarmInteractorImpl @Inject constructor(private val alarmManager: TaskNotificationScheduler) :
    AlarmInteractor {

    override fun schedule(alarmId: Long, timeInMillis: Long) {
        logcat { "schedule - alarmId = $alarmId" }
        alarmManager.scheduleTaskAlarm(alarmId, timeInMillis)
    }

    override fun cancel(alarmId: Long) {
        logcat { "cancel - alarmId = $alarmId" }
        alarmManager.cancelTaskAlarm(alarmId)
    }
}
