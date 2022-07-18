package com.remindme.domain.usecase.preferences

import com.remindme.domain.model.AppThemeOptions
import javax.inject.Inject

/**
 * Updates the current app theme.
 *
 * @property preferencesRepository the preferences repository
 */
class UpdateAppTheme(private val preferencesRepository: PreferencesRepository) {

    /**
     * Updates the current app theme.
     *
     * @param theme the theme to be updated
     */
    suspend operator fun invoke(theme: AppThemeOptions) =
        preferencesRepository.updateAppTheme(theme)
}
