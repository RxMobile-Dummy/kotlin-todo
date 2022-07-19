package com.remindme.alarm

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.remindme.domain.usecase.alarm.RescheduleFutureAlarms
import com.remindme.domain.usecase.alarm.ShowAlarm
import com.remindme.domain.usecase.alarm.SnoozeAlarm
import com.remindme.domain.usecase.task.CompleteTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
import javax.inject.Inject

/**
 * [BroadcastReceiver] to be notified by the [android.app.AlarmManager].
 */
internal class TaskReceiver : BroadcastReceiver() /*KoinComponent*/ {

    @Inject lateinit var coroutineScope: CoroutineScope

    @Inject lateinit var completeTaskUseCase: CompleteTask

    @Inject lateinit var showAlarmUseCase: ShowAlarm

    @Inject lateinit var snoozeAlarmUseCase: SnoozeAlarm

    @Inject lateinit var rescheduleUseCase: RescheduleFutureAlarms

    override fun onReceive(context: Context?, intent: Intent?) {
        logcat { "onReceive() - intent ${intent?.action}" }

        coroutineScope.launch {
            handleIntent(intent)
        }
    }

    private suspend fun handleIntent(intent: Intent?) =
        when (intent?.action) {
            ALARM_ACTION -> getTaskId(intent)?.let { showAlarmUseCase(it) }
            COMPLETE_ACTION -> getTaskId(intent)?.let { completeTaskUseCase(it) }
            SNOOZE_ACTION -> getTaskId(intent)?.let { snoozeAlarmUseCase(it.toLong()) }
            Intent.ACTION_BOOT_COMPLETED,
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> rescheduleUseCase()
            else -> logcat(LogPriority.ERROR) { "Action not supported" }
        }

    private fun getTaskId(intent: Intent?) = intent?.getIntExtra(EXTRA_TASK, 0)

    companion object {

        const val EXTRA_TASK = "extra_task"

        const val ALARM_ACTION = "com.remindme.SET_ALARM"

        const val COMPLETE_ACTION = "com.remindme.SET_COMPLETE"

        const val SNOOZE_ACTION = "com.remindme.SNOOZE"
    }
}
