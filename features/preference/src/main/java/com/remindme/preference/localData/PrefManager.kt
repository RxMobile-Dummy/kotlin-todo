package com.remindme.preference.localData

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey

object PrefManager {
    const val PREFERENCES_NOTIFICATION =  "sample_datastore_prefs"

    val Is_Notification_Enable = booleanPreferencesKey("is_notification_enable")

}