package com.remindme.alarm

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.usecase.alarm.RescheduleFutureAlarms
import com.remindme.domain.usecase.alarm.ScheduleNextAlarm
import com.remindme.domain.usecase.alarm.ShowAlarm
import com.remindme.domain.usecase.alarm.SnoozeAlarm
import com.remindme.domain.usecase.task.CompleteTask
import com.remindme.preference.localData.Notification
import com.remindme.preference.localData.NotificationImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import logcat.LogPriority
import logcat.logcat
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
import javax.inject.Inject
import androidx.datastore.preferences.preferencesDataStore
import com.remindme.preference.localData.PrefClass
import com.remindme.preference.localData.PrefClass.Companion.dataStore
import com.remindme.preference.localData.PrefManager

/**
 * [BroadcastReceiver] to be notified by the [android.app.AlarmManager].
 */
@AndroidEntryPoint
class TaskReceiver @Inject constructor() : HiltBroadcastReceiver() /*KoinComponent*/ {


    //lateinit var prefsDataStore: DataStore<Preferences>

    @Inject
    lateinit var coroutineScope: CoroutineScope

    @Inject
    lateinit var completeTaskUseCase: CompleteTask

    @Inject
    lateinit var showAlarmUseCase: ShowAlarm

    @Inject
    lateinit var snoozeAlarmUseCase: SnoozeAlarm

    @Inject
    lateinit var rescheduleUseCase: RescheduleFutureAlarms
    private var notification: NotificationImpl? = null
//     val Context.appManagerDataStore
//               by preferencesDataStore(name = PrefManager.PREFERENCES_NOTIFICATION)
   // var prefClass:PrefClass = PrefClass()



    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        logcat { "onReceive() - intent ${intent?.action}" }


        coroutineScope = CoroutineScope(Dispatchers.Default)
        notification = NotificationImpl(context?.dataStore!!)

        coroutineScope.launch {
            notification?.getNotificationState()?.collect { state ->
                logcat { "status() - intent ${state}" }
                if (state) {
                    handleIntent(intent)
                }
            }
        }
    }

    private suspend fun handleIntent(intent: Intent?) =
        when (intent?.action) {
            ALARM_ACTION -> getTaskId(intent)?.let { showAlarmUseCase(it) }
            COMPLETE_ACTION -> getTaskId(intent)?.let { completeTaskUseCase(it) }
            SNOOZE_ACTION -> getTaskId(intent)?.let { snoozeAlarmUseCase(it) }
            Intent.ACTION_BOOT_COMPLETED,
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> rescheduleUseCase()
            else -> logcat(LogPriority.ERROR) { "Action not supported" }
        }

    private fun getTaskId(intent: Intent?) = intent?.getLongExtra(EXTRA_TASK, 0)

    companion object {

        const val EXTRA_TASK = "extra_task"

        const val ALARM_ACTION = "com.remindme.SET_ALARM"

        const val COMPLETE_ACTION = "com.remindme.SET_COMPLETE"

        const val SNOOZE_ACTION = "com.remindme.SNOOZE"
    }
}






