package com.remindme.alarm.di

import android.app.AlarmManager
import android.content.Context
import com.remindme.alarm.interactor.AlarmInteractorImpl
import com.remindme.alarm.interactor.NotificationInteractorImpl
import com.remindme.alarm.mapper.TaskMapper
import com.remindme.alarm.notification.TaskNotification
import com.remindme.alarm.notification.TaskNotificationChannel
import com.remindme.alarm.notification.TaskNotificationScheduler
import com.remindme.alarm.permission.AlarmPermissionImpl
import com.remindme.alarm.permission.AndroidVersion
import com.remindme.alarm.permission.AndroidVersionImpl
import com.remindme.alarmapi.AlarmPermission
import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.interactor.NotificationInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Alarm dependency injection module.
 */
@Module
@InstallIn(SingletonComponent::class)
class AlarmModule{
    @Provides
    fun taskNotificationScheduler(
        @ApplicationContext context: Context,
    ): TaskNotificationScheduler {
        return TaskNotificationScheduler(context)
    }
    @Provides
    fun taskNotificationChannel(
        @ApplicationContext context: Context,
        ): TaskNotificationChannel {
        return TaskNotificationChannel(context)
    }
    @Provides
    fun taskNotification(
        @ApplicationContext context: Context,
            taskNotificationChannel: TaskNotificationChannel
        ): TaskNotification {
        return TaskNotification(context,taskNotificationChannel)
    }
    @Provides
    fun taskMapper(
    ): TaskMapper {
        return TaskMapper()
    }
    @Provides
    fun alarmInteractor(
        taskNotificationScheduler: TaskNotificationScheduler
    ): AlarmInteractor {
        return AlarmInteractorImpl(taskNotificationScheduler)
    }
    @Provides
    fun notificationInteractor(
        taskNotification: TaskNotification,
        taskMapper: TaskMapper
    ): NotificationInteractor {
        return NotificationInteractorImpl(taskNotification,taskMapper)
    }
    @Provides
    fun getAndroidVersion(
    ): AndroidVersion {
        return AndroidVersionImpl()
    }
    @Provides
    fun getAlarmPermission(
        alarmManager: AlarmManager?,
        androidVersion: AndroidVersion
    ): AlarmPermission {
        return AlarmPermissionImpl(alarmManager,androidVersion)
    }
}