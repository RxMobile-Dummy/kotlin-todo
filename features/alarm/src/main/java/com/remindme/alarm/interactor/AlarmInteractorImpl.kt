package com.remindme.alarm.interactor

import com.remindme.alarm.notification.TaskNotificationScheduler
import com.remindme.domain.interactor.AlarmInteractor
import logcat.logcat
import java.util.*
import javax.inject.Inject

class AlarmInteractorImpl @Inject constructor(private val alarmManager: TaskNotificationScheduler) :
    AlarmInteractor {

    override fun schedule(alarmId: Long, timeInMillis: Long,isAdd:Boolean) {
        logcat { "schedule - alarmId = $alarmId" }
        if(isAdd) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, 3)
            alarmManager.scheduleTaskAlarm(alarmId, calendar.timeInMillis, isAdd)
        }
            else
            {
                alarmManager.scheduleTaskAlarm(alarmId,timeInMillis,isAdd)
            }

    }

    override fun cancel(alarmId: Long) {
        logcat { "cancel - alarmId = $alarmId" }
        alarmManager.cancelTaskAlarm(alarmId)
    }
}
