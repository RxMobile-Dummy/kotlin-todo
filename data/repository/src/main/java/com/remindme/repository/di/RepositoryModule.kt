package com.remindme.repository.di

import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.repository.SearchRepository
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.repository.TaskWithCategoryRepository
import com.remindme.domain.usecase.preferences.PreferencesRepository
import com.remindme.repository.CategoryRepositoryImpl
import com.remindme.repository.PreferencesRepositoryImpl
import com.remindme.repository.SearchRepositoryImpl
import com.remindme.repository.TaskRepositoryImpl
import com.remindme.repository.TaskWithCategoryRepositoryImpl
import com.remindme.repository.datasource.*
import com.remindme.repository.mapper.AlarmIntervalMapper
import com.remindme.repository.mapper.AppThemeOptionsMapper
import com.remindme.repository.mapper.CategoryMapper
import com.remindme.repository.mapper.TaskMapper
import com.remindme.repository.mapper.TaskWithCategoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repository dependency injection module.
 */

@Module
@InstallIn(SingletonComponent::class)
 class RepositoryModule {

    @Provides
    fun getTaskRepository(
        taskDataSource: TaskDataSource,
        taskMapper: TaskMapper
    ): TaskRepository{
        return TaskRepositoryImpl(taskDataSource,taskMapper)
    }

    @Provides
     fun getCategoryRepository(
         categoryDataSource: CategoryDataSource,
         categoryMapper: CategoryMapper
    ): CategoryRepository{
         return CategoryRepositoryImpl(categoryDataSource,categoryMapper)
     }


    @Provides
     fun getTaskWithCategoryRepository(
         dataSource: TaskWithCategoryDataSource,
         mapper: TaskWithCategoryMapper
    ): TaskWithCategoryRepository{
        return TaskWithCategoryRepositoryImpl(dataSource,mapper)
    }


    @Provides
     fun getSearchRepository(
         searchDataSource: SearchDataSource,
         mapper: TaskWithCategoryMapper
    ): SearchRepository{
         return SearchRepositoryImpl(searchDataSource,mapper)
     }

    @Provides
     fun getPreferencesRepository(
        dataSource: PreferencesDataSource,
        mapper: AppThemeOptionsMapper
    ): PreferencesRepository{
         return PreferencesRepositoryImpl(dataSource,mapper)
     }

    @Provides
     fun getAlarmIntervalMapper(
    ): AlarmIntervalMapper{
         return AlarmIntervalMapper()
     }

    @Provides
     fun getTaskMapper(
        alarmIntervalMapper: AlarmIntervalMapper
    ): TaskMapper{
         return TaskMapper(alarmIntervalMapper)
     }


    @Provides
     fun getCategoryMapper(
    ): CategoryMapper{
         return CategoryMapper()
     }


    @Provides
     fun getTaskWithCategoryMapper(
        taskMapper: TaskMapper,
        categoryMapper: CategoryMapper

    ): TaskWithCategoryMapper{
        return TaskWithCategoryMapper(taskMapper,categoryMapper)
    }

    @Provides
     fun getAppThemeOptionsMapper(
    ): AppThemeOptionsMapper{
         return AppThemeOptionsMapper()
     }
}