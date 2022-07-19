package com.remindme.repository.mapper

import javax.inject.Inject
import com.remindme.domain.model.AppThemeOptions as DomainThemeOptions
import com.remindme.repository.model.AppThemeOptions as RepoThemeOptions

/**
 * Maps AppThemeOptions between Repository and Domain.
 */
class AppThemeOptionsMapper @Inject constructor() {

    /**
     * Maps AppThemeOptions from Repo to Domain.
     *
     * @param appThemeOptions the object to be converted
     *
     * @return the converted object
     */
    fun toDomain(appThemeOptions: RepoThemeOptions): DomainThemeOptions =
        when (appThemeOptions) {
            RepoThemeOptions.LIGHT -> DomainThemeOptions.LIGHT
            RepoThemeOptions.DARK -> DomainThemeOptions.DARK
            RepoThemeOptions.SYSTEM -> DomainThemeOptions.SYSTEM
        }

    /**
     * Maps AppThemeOptions from Domain to Repo.
     *
     * @param appThemeOptions the object to be converted
     *
     * @return the converted object
     */
    fun toRepo(appThemeOptions: DomainThemeOptions): RepoThemeOptions =
        when (appThemeOptions) {
            DomainThemeOptions.LIGHT -> RepoThemeOptions.LIGHT
            DomainThemeOptions.DARK -> RepoThemeOptions.DARK
            DomainThemeOptions.SYSTEM -> RepoThemeOptions.SYSTEM
        }
}
