package com.remindme.tracker.di

import com.remindme.domain.usecase.tracker.LoadCompletedTasksByPeriod
import com.remindme.tracker.mapper.TrackerMapper
import com.remindme.tracker.presentation.TrackerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Injects the injection modules in the dynamic feature. This function needs to be called from every
 * entry point for this feature.
 */


@Module
@InstallIn(SingletonComponent::class)
class TrackerModule {

    @Provides
    fun trackerViewModel(
        loadCompletedTasksByPeriod: LoadCompletedTasksByPeriod,
        trackerMapper: TrackerMapper
    ): TrackerViewModel {
        return TrackerViewModel(loadCompletedTasksByPeriod, trackerMapper)
    }

    @Provides
    fun trackerMapper(
    ): TrackerMapper {
        return TrackerMapper()
    }
}