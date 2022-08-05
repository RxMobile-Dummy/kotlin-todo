package com.remindme.preference.localData

import kotlinx.coroutines.flow.Flow

interface  Notification {
    suspend fun saveNotificationState(state:Boolean)
    suspend fun getNotificationState(): Flow<Boolean>
}