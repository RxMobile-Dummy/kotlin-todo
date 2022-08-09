package com.remindme.preference.localData

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


class PrefClass(context: Context) {
    companion object {
         val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PrefManager.PREFERENCES_NOTIFICATION)

    }
}