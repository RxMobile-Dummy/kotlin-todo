package com.remindme.domain.usecase.preferences

import com.remindme.domain.model.AppThemeOptions
import dagger.Binds
import dagger.Module
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

/**
 * Interface to represent the implementation of Preferences repository.
 */

interface PreferencesRepository {

    /**
     * Updates the current app theme.
     *
     * @param theme the theme to be updated
     */
    suspend fun updateAppTheme(theme: AppThemeOptions)

    /**
     * Loads the current app theme.
     *
     * @return flow of [AppThemeOptions]
     */
      fun loadAppTheme(): Flow<AppThemeOptions>
}
