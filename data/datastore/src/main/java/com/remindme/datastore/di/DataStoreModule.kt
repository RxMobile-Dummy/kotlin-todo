package com.remindme.datastore.di

import android.content.Context
import com.remindme.datastore.datasource.PreferencesDataSourceImpl
import com.remindme.datastore.mapper.AppThemeOptionsMapper
import com.remindme.repository.datasource.PreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import org.koin.android.ext.koin.androidContext
//import org.koin.dsl.module

/**
 * DataStore dependency injection module.
 */
//val dataStoreModule = module {
//
//    // Data Source
//    factory<PreferencesDataSource> { PreferencesDataSourceImpl(androidContext(), get()) }
//
//    // Mappers
//    factory { AppThemeOptionsMapper() }
//}
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun getPreferencesDataSource(
        @ApplicationContext context: Context,
        appThemeOptionsMapper: AppThemeOptionsMapper
    ): PreferencesDataSource {
        return PreferencesDataSourceImpl(context, appThemeOptionsMapper)
    }



    @Provides
    @Singleton
    fun appThemeOptionMapper(
    ): AppThemeOptionsMapper {
        return AppThemeOptionsMapper()
    }
}