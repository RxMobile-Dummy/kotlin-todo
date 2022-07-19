package com.remindme.datastore.mapper

import com.remindme.datastore.model.AppThemeOptions as DataStoreThemeOptions
import com.remindme.repository.model.AppThemeOptions as RepoThemeOptions

/**
 * Maps AppThemeOptions between Repository and DataStore.
 */
class AppThemeOptionsMapper {

    /**
     * Maps AppThemeOptions from DataStore to Domain.
     *
     * @param appThemeOptions the object to be converted
     *
     * @return the converted object
     */
    fun toDataStore(appThemeOptions: RepoThemeOptions): DataStoreThemeOptions =
        when (appThemeOptions) {
            RepoThemeOptions.LIGHT -> DataStoreThemeOptions.LIGHT
            RepoThemeOptions.DARK -> DataStoreThemeOptions.DARK
            RepoThemeOptions.SYSTEM -> DataStoreThemeOptions.SYSTEM
        }

    /**
     * Maps AppThemeOptions from DataStore to Repo.
     *
     * @param appThemeOptions the object to be converted
     *
     * @return the converted object
     */
    fun toRepo(appThemeOptions: DataStoreThemeOptions): RepoThemeOptions =
        when (appThemeOptions) {
            DataStoreThemeOptions.LIGHT -> RepoThemeOptions.LIGHT
            DataStoreThemeOptions.DARK -> RepoThemeOptions.DARK
            DataStoreThemeOptions.SYSTEM -> RepoThemeOptions.SYSTEM
        }
}
