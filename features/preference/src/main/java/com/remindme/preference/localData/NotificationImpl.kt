package com.remindme.preference.localData

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class NotificationImpl @Inject constructor(private val prefsDataStore: DataStore<Preferences>):Notification {
    override suspend fun saveNotificationState(state: Boolean) {
        prefsDataStore.edit { preferences ->
            preferences[PrefManager.Is_Notification_Enable] = state
        }
    }

    override suspend fun getNotificationState(): Flow<Boolean> {
        return prefsDataStore.data
                .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PrefManager.Is_Notification_Enable]?:false
        }
    }
    }

