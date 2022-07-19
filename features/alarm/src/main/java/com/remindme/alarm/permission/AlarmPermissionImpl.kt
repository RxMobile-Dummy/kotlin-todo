package com.remindme.alarm.permission

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.os.Build
import com.remindme.alarmapi.AlarmPermission
import javax.inject.Inject

internal class AlarmPermissionImpl @Inject constructor(
    private val alarmManager: AlarmManager?,
    private val androidVersion: AndroidVersion
) : AlarmPermission {

    @SuppressLint("NewApi")
    override fun hasExactAlarmPermission(): Boolean {
        if (alarmManager == null) return false

        return if (androidVersion.currentVersion >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}
