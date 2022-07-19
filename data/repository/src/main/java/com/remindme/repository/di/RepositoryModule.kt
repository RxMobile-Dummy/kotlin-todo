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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//import org.koin.dsl.module

/**
 * Repository dependency injection module.
 */
//val repositoryModule = module {
//
//    // Repositories
//    single<TaskRepository> { TaskRepositoryImpl(get(), get()) }
//    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
//    single<TaskWithCategoryRepository> { TaskWithCategoryRepositoryImpl(get(), get()) }
//    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
//    single<PreferencesRepository> { PreferencesRepositoryImpl(get(), get()) }
//
//    // Mappers
//    factory { AlarmIntervalMapper() }
//    factory { TaskMapper(get()) }
//    factory { CategoryMapper() }
//    factory { TaskWithCategoryMapper(get(), get()) }
//    factory { AppThemeOptionsMapper() }
//}
@Module
@InstallIn(SingletonComponent::class)
abstract  class  RepositoryModule {

//    @Binds
//    abstract fun getTaskRepository(
//        taskDataSource: TaskDataSource,
//        taskMapper: TaskMapper
//    ): TaskRepositoryImpl
//
//

//    @Provides
//    fun getCategoryRepository(
//        categoryDataSource: CategoryDataSource,
//        categoryMapper: CategoryMapper
//
//    ): CategoryRepository {
//        return CategoryRepositoryImpl(categoryDataSource, categoryMapper)
//    }

//    @Binds
//    abstract fun bindsLocationRepository(
//        categoryRepositoryImpl: CategoryRepositoryImpl
//    ): CategoryRepository
//
//    @Binds
//    abstract fun getTaskWithCategoryRepository(
//        taskWithCategoryDataSource: TaskWithCategoryDataSource,
//        taskWithCategoryMapper: TaskWithCategoryMapper,
//            taskWithCategoryRepositoryImpl: TaskWithCategoryRepositoryImpl
//
//    ): TaskWithCategoryRepository
//
//
//    @Binds
//    abstract fun getSearchRepository(
//        searchDataSource: SearchDataSource,
//        taskWithCategoryMapper: TaskWithCategoryMapper,
//        searchRepository: SearchRepository,
//
//    ): SearchRepository

    @Binds
    abstract fun getPreferencesRepository(
      preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository
//
//
//    @Provides
//    fun getAlarmIntervalMapper(
//    ): AlarmIntervalMapper {
//        return AlarmIntervalMapper()
//    }
//
//    @Provides
//    fun getTaskMapper(
//        alarmIntervalMapper: AlarmIntervalMapper
//    ): TaskMapper {
//        return TaskMapper(alarmIntervalMapper   )
//    }
//
//
//    @Provides
//    fun getCategoryMapper(
//    ): CategoryMapper {
//        return CategoryMapper()
//    }
//
//
//    @Provides
//    fun getTaskWithCategoryMapper(
//        taskMapper: TaskMapper,
//        categoryMapper: CategoryMapper
//    ): TaskWithCategoryMapper {
//        return TaskWithCategoryMapper(taskMapper,categoryMapper)
//    }
//
//    @Provides
//    fun getAppThemeOptionsMapper(
//    ): AppThemeOptionsMapper {
//        return AppThemeOptionsMapper()
//    }
}