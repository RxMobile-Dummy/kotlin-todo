package com.todotask.presentation.detail.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remindme.domain.usecase.alarm.CancelAlarm
import com.remindme.domain.usecase.alarm.ScheduleAlarm
import com.remindme.domain.usecase.alarm.UpdateTaskAsRepeating
import com.todotask.mapper.AlarmIntervalMapper
import com.todotask.model.AlarmInterval
import com.todotask.presentation.detail.main.TaskId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class TaskAlarmViewModel @Inject constructor(
    private val scheduleAlarmUseCase: ScheduleAlarm,
    private val updateTaskAsRepeatingUseCase: UpdateTaskAsRepeating,
    private val cancelAlarmUseCase: CancelAlarm,
    private val alarmIntervalMapper: AlarmIntervalMapper
) : ViewModel() {

    fun updateAlarm(taskId: TaskId, alarm: Calendar?,isAdd:Boolean) = viewModelScope.launch {
        if (alarm != null) {
            scheduleAlarmUseCase(taskId.value, alarm,isAdd)
        } else {
            cancelAlarmUseCase(taskId.value)
        }
    }

    fun setRepeating(taskId: TaskId, alarmInterval: AlarmInterval) = viewModelScope.launch {
        val interval = alarmIntervalMapper.toDomain(alarmInterval)
        updateTaskAsRepeatingUseCase(taskId.value, interval)
    }
}
