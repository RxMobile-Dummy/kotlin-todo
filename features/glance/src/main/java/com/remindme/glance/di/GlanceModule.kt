package com.remindme.glance.di

import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.glance.interactor.GlanceInteractorImpl
import com.remindme.glance.mapper.TaskMapper
import com.remindme.glance.presentation.TaskListGlanceViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import org.koin.dsl.module

/**
 * Glance dependency injection module.
 */
//val glanceModule = module {
//
//    // Presentation
//    factory { TaskListGlanceViewModel(get(), get(), get()) }
//
//    // Interactor
//    factory<GlanceInteractor> { GlanceInteractorImpl() }
//
//    // Mapper
//    factory { TaskMapper() }
//}
@Module
@InstallIn(SingletonComponent::class)
object GlanceModule {

    @Provides
    @ViewModelScoped
    fun taskListGlanceViewModel(
        loadUncompletedTasks: LoadUncompletedTasks,
        updateTaskStatus: UpdateTaskStatus,
        taskMapper: TaskMapper
    ): TaskListGlanceViewModel {
        return TaskListGlanceViewModel(loadUncompletedTasks, updateTaskStatus,taskMapper)
    }

    @Provides
    @Singleton
    fun glanceInteractor(
    ): GlanceInteractor {
        return GlanceInteractorImpl()
    }
    @Provides
    @Singleton
    fun taskMapper(
    ): TaskMapper {
        return TaskMapper()
    }

}