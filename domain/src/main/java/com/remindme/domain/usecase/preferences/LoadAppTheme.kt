package com.remindme.domain.usecase.preferences

import com.remindme.domain.model.AppThemeOptions
import com.remindme.domain.repository.TaskWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Loads the current app theme.
 *
 * @property preferencesRepository the preferences repository
 */
class LoadAppTheme @Inject constructor(
    private val preferencesRepository: PreferencesRepository
){

    /**
     * Loads the current app theme.
     *
     * @return flow of [AppThemeOptions]
     */
     operator fun invoke(): Flow<AppThemeOptions> =
        preferencesRepository.loadAppTheme()
}
