package com.remindme.presentation.mapper

import javax.inject.Inject
import com.remindme.presentation.model.AppThemeOptions as ViewDataThemeOptions
import com.remindme.domain.model.AppThemeOptions as DomainThemeOptions

/**
 * Maps AppThemeOptions between Repository and DataStore.
 */

 class AppThemeOptionsMapper() {

    /**
     * Maps AppThemeOptions from DataStore to Domain.
     *
     * @param appThemeOptions the object to be converted
     *
     * @return the converted object
     */
    fun toViewData(appThemeOptions: DomainThemeOptions): ViewDataThemeOptions =
        when (appThemeOptions) {
            DomainThemeOptions.LIGHT -> ViewDataThemeOptions.LIGHT
            DomainThemeOptions.DARK -> ViewDataThemeOptions.DARK
            DomainThemeOptions.SYSTEM -> ViewDataThemeOptions.SYSTEM
        }
}
