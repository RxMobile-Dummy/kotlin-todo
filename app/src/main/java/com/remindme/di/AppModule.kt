package com.remindme.di

import android.app.AlarmManager
import android.content.Context
import com.remindme.core.extension.getAlarmManager
import com.remindme.domain.usecase.preferences.LoadAppTheme
import com.remindme.presentation.MainViewModel
import com.remindme.presentation.mapper.AppThemeOptionsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun getAlarmManager(
        @ApplicationContext context: Context
    ): AlarmManager? {
        return context.getAlarmManager()
    }
    @Provides
    fun getCoroutineScope(
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    @Provides
    fun getMainViewModel(
        loadAppTheme: LoadAppTheme,
        appThemeOptionsMapper: AppThemeOptionsMapper
    ): MainViewModel {
        return MainViewModel(loadAppTheme, appThemeOptionsMapper)
    }

    @Provides
    fun getAppThemeOptionsMapper(): AppThemeOptionsMapper = AppThemeOptionsMapper()


}