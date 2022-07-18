package com.remindme.tracker.di

import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.tracker.LoadCompletedTasksByPeriod
import com.remindme.tracker.mapper.TrackerMapper
import com.remindme.tracker.presentation.TrackerViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.context.loadKoinModules
//import org.koin.dsl.module

/**
 * Injects the injection modules in the dynamic feature. This function needs to be called from every
 * entry point for this feature.
 */
//fun injectDynamicFeature() = loadFeatureModules
//
//private val loadFeatureModules by lazy {
//    loadKoinModules(trackerModule)
//}
//
///**
// * Tracker dependency injection module.
// */
//val trackerModule = module {
//    viewModel { TrackerViewModel(get(), get()) }
//    factory { TrackerMapper() }
//}
/**
 * Injects the injection modules in the dynamic feature. This function needs to be called from every
 * entry point for this feature.
 */
//fun injectDynamicFeature() = loadFeatureModules
//
//private val loadFeatureModules by lazy {
//}

@Module
@InstallIn(SingletonComponent::class)
object TrackerModule {

    @Provides
    @ViewModelScoped
    fun trackerViewModel(
        loadCompletedTasksByPeriod: LoadCompletedTasksByPeriod,
        trackerMapper: TrackerMapper
    ): TrackerViewModel {
        return TrackerViewModel(loadCompletedTasksByPeriod, trackerMapper)
    }

    @Provides
    @Singleton
    fun trackerMapper(
    ): TrackerMapper {
        return TrackerMapper()
    }
}