package com.remindme.repository

import com.remindme.domain.model.AppThemeOptions
import com.remindme.domain.usecase.preferences.PreferencesRepository
import com.remindme.repository.datasource.PreferencesDataSource
import com.remindme.repository.mapper.AppThemeOptionsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

 class PreferencesRepositoryImpl @Inject constructor(
    private val dataSource: PreferencesDataSource,
    private val mapper: AppThemeOptionsMapper
) : PreferencesRepository {

    override suspend fun updateAppTheme(theme: AppThemeOptions) =
        dataSource.updateAppTheme(mapper.toRepo(theme))

    override fun loadAppTheme(): Flow<AppThemeOptions> =
        dataSource.loadAppTheme().map { mapper.toDomain(it) }
}
