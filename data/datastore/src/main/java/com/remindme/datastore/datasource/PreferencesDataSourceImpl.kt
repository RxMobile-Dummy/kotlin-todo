package com.remindme.datastore.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.remindme.datastore.dataStore
import com.remindme.datastore.mapper.AppThemeOptionsMapper
import com.remindme.repository.datasource.PreferencesDataSource
import com.remindme.repository.model.AppThemeOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.remindme.datastore.model.AppThemeOptions as DataStoreThemeOptions

internal class PreferencesDataSourceImpl @Inject constructor(
    private val context: Context,
    private val mapper: AppThemeOptionsMapper
) : PreferencesDataSource {

    override suspend fun updateAppTheme(theme: AppThemeOptions) {
        context.dataStore.edit { settings ->
            settings[APP_THEME_OPTION] = mapper.toDataStore(theme).id
        }
    }

    override fun loadAppTheme(): Flow<AppThemeOptions> =
        context.dataStore.data.map { preferences ->
            val id = preferences[APP_THEME_OPTION] ?: DataStoreThemeOptions.SYSTEM.id
            val result = DataStoreThemeOptions.values().find { it.id == id } ?: DataStoreThemeOptions.SYSTEM
            mapper.toRepo(result)
        }

    private companion object {
        val APP_THEME_OPTION = intPreferencesKey("remindme_theme_option")
    }
}
