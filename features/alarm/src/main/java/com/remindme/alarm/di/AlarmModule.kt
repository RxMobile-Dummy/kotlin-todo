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

//import org.koin.android.ext.koin.androidContext
//import org.koin.dsl.module

/**
 * Alarm dependency injection module.
 */
//val alarmModule = module {
//
//    factory { TaskNotificationScheduler(androidContext()) }
//    factory { TaskNotificationChannel(androidContext()) }
//    factory { TaskNotification(androidContext(), get()) }
//
//    factory { TaskMapper() }
//
//    factory<AlarmInteractor> { AlarmInteractorImpl(get()) }
//    factory<NotificationInteractor> { NotificationInteractorImpl(get(), get()) }
//
//    factory<AndroidVersion> { AndroidVersionImpl() }
//    factory<AlarmPermission> { AlarmPermissionImpl(get(), get()) }
//}
@Module
@InstallIn(SingletonComponent::class)
object AlarmModule{
    @Provides
    @Singleton
    fun taskNotificationScheduler(
        @ApplicationContext context: Context,
    ): TaskNotificationScheduler {
        return TaskNotificationScheduler(context)
    }
    @Provides
    @Singleton
    fun taskNotificationChannel(
        @ApplicationContext context: Context,
        ): TaskNotificationChannel {
        return TaskNotificationChannel(context)
    }
    @Provides
    @Singleton
    fun taskNotification(
        @ApplicationContext context: Context,
            taskNotificationChannel: TaskNotificationChannel
        ): TaskNotification {
        return TaskNotification(context,taskNotificationChannel)
    }
    @Provides
    @Singleton
    fun taskMapper(
    ): TaskMapper {
        return TaskMapper()
    }
    @Provides
    @Singleton
    fun alarmInteractor(
        taskNotificationScheduler: TaskNotificationScheduler
    ): AlarmInteractor {
        return AlarmInteractorImpl(taskNotificationScheduler)
    }
    @Provides
    @Singleton
    fun notificationInteractor(
        taskNotification: TaskNotification,
        taskMapper: TaskMapper
    ): NotificationInteractor {
        return NotificationInteractorImpl(taskNotification,taskMapper)
    }
    @Provides
    @Singleton
    fun getAndroidVersion(
    ): AndroidVersion {
        return AndroidVersionImpl()
    }
    @Provides
    @Singleton
    fun getAlarmPermission(
        alarmManager: AlarmManager,
        androidVersion: AndroidVersion
    ): AlarmPermission {
        return AlarmPermissionImpl(alarmManager,androidVersion)
    }

}