package com.remindme.local.di

import android.content.Context
import com.remindme.local.datasource.CategoryLocalDataSource
import com.remindme.local.datasource.SearchLocalDataSource
import com.remindme.local.datasource.TaskLocalDataSource
import com.remindme.local.datasource.TaskWithCategoryLocalDataSource
import com.remindme.local.mapper.AlarmIntervalMapper
import com.remindme.local.mapper.CategoryMapper
import com.remindme.local.mapper.TaskMapper
import com.remindme.local.mapper.TaskWithCategoryMapper
import com.remindme.local.provider.DaoProvider
import com.remindme.local.provider.DatabaseProvider
import com.remindme.repository.datasource.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

//import org.koin.dsl.module

/**
 * Local dependency injection module.
 */
//@Module
//@InstallIn(SingletonComponent::class)
//class localModule {
//    @Provides
//    fun getPreferenceDataStore(): TaskDataSource = TaskLocalDataSource()
//
//
//    @Provides
//    fun getAppThemeOptionsMapper(): CategoryDataSource = CategoryLocalDataSource(getCategoryMapper(),getDaoProvider())
//
//
//    // Mappers
//    @Binds
//    fun getAlarmIntervalMapper(): AlarmIntervalMapper = AlarmIntervalMapper()
//
//    @Binds
//    fun getTaskMapper(): TaskMapper = TaskMapper(getAlarmIntervalMapper())
//
//    @Provides
//    fun getCategoryMapper(): CategoryMapper = CategoryMapper()
//
//    @Provides
//    fun getTaskWithCategoryMapper(): TaskWithCategoryMapper = TaskWithCategoryMapper(get(), get())
//
//    // Providers
//    @Provides
//    fun getDatabaseProvider(): DatabaseProvider = DatabaseProvider(get(), get())
//
//    @Provides
//    fun getDaoProvider(): DaoProvider = DaoProvider(get())
//}
//val localModule = module {
//
//    // Data Sources
//    single<TaskDataSource> { TaskLocalDataSource(get(), get()) }
//    single<CategoryDataSource> { CategoryLocalDataSource(get(), get()) }
//    single<TaskWithCategoryDataSource> { TaskWithCategoryLocalDataSource(get(), get()) }
//    single<SearchDataSource> { SearchLocalDataSource(get(), get()) }
//
//    // Mappers
//    factory { AlarmIntervalMapper() }
//    factory { TaskMapper(get()) }
//    factory { CategoryMapper() }
//    factory { TaskWithCategoryMapper(get(), get()) }
//
//    // Providers
//    single { DatabaseProvider(get(), get()) }
//    single { DaoProvider(get()) }
//}
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun getTaskDataSource(
        daoProvider: DaoProvider,
        taskMapper: TaskMapper
    ): TaskLocalDataSource {
        return TaskLocalDataSource(daoProvider, taskMapper)
    }

    @Provides
    fun getCategoryDataSource(
        daoProvider: DaoProvider,
        categoryMapper: CategoryMapper
    ): CategoryDataSource {
        return CategoryLocalDataSource(daoProvider, categoryMapper)
    }

    @Provides
    fun getTaskCategoryDataSource(
        daoProvider: DaoProvider,
        taskWithCategoryMapper: TaskWithCategoryMapper
    ): TaskWithCategoryDataSource {
        return TaskWithCategoryLocalDataSource(daoProvider, taskWithCategoryMapper)
    }
    @Provides
    fun searchDataSource(
        daoProvider: DaoProvider,
        taskWithCategoryMapper: TaskWithCategoryMapper
    ): SearchDataSource {
        return SearchLocalDataSource(daoProvider, taskWithCategoryMapper)
    }


    @Provides
    @Singleton
    fun alarmIntervalMapper(
    ): AlarmIntervalMapper {
        return AlarmIntervalMapper()
    }

    @Provides
    @Singleton
    fun taskMapper(
        alarmIntervalMapper: AlarmIntervalMapper
    ): TaskMapper {
        return TaskMapper(alarmIntervalMapper)
    }

    @Provides
    @Singleton
    fun categoryMapper(
    ): CategoryMapper {
        return CategoryMapper()
    }

    @Provides
    @Singleton
    fun taskWithCategoryMapper(
        taskMapper: TaskMapper,
        categoryMapper: CategoryMapper
    ): TaskWithCategoryMapper {
        return TaskWithCategoryMapper(taskMapper,categoryMapper)
    }

    @Provides
    @Singleton
    fun databaseProvider(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ): DatabaseProvider {
        return DatabaseProvider(context,coroutineScope)
    }
    @Provides
    @Singleton
    fun daoProvider(
        databaseProvider: DatabaseProvider
    ): DaoProvider {
        return DaoProvider(databaseProvider)
    }
}