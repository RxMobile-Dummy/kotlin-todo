package com.remindme.domain.di

import com.remindme.domain.interactor.AlarmInteractor
import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.interactor.NotificationInteractor
import com.remindme.domain.provider.CalendarProvider
import com.remindme.domain.provider.CalendarProviderImpl
import com.remindme.domain.repository.CategoryRepository
import com.remindme.domain.repository.SearchRepository
import com.remindme.domain.repository.TaskRepository
import com.remindme.domain.repository.TaskWithCategoryRepository
import com.remindme.domain.usecase.alarm.*
import com.remindme.domain.usecase.alarm.implementation.CancelAlarmImpl
import com.remindme.domain.usecase.alarm.implementation.ScheduleAlarmImpl
import com.remindme.domain.usecase.alarm.implementation.UpdateTaskAsRepeatingImpl
import com.remindme.domain.usecase.category.*
import com.remindme.domain.usecase.category.implementation.AddCategoryImpl
import com.remindme.domain.usecase.category.implementation.DeleteCategoryImpl
import com.remindme.domain.usecase.category.implementation.LoadAllCategoriesImpl
import com.remindme.domain.usecase.category.implementation.UpdateCategoryImpl
import com.remindme.domain.usecase.preferences.LoadAppTheme
import com.remindme.domain.usecase.preferences.PreferencesRepository
import com.remindme.domain.usecase.preferences.UpdateAppTheme
import com.remindme.domain.usecase.search.SearchTasksByName
import com.remindme.domain.usecase.search.implementation.SearchTasksByNameImpl
import com.remindme.domain.usecase.task.*
import com.remindme.domain.usecase.task.implementation.*
import com.remindme.domain.usecase.taskwithcategory.LoadCompletedTasks
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.domain.usecase.taskwithcategory.implementation.LoadUncompletedTasksImpl
import com.remindme.domain.usecase.tracker.LoadCompletedTasksByPeriod
import com.remindme.domain.usecase.tracker.implementation.LoadCompletedTasksByPeriodImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//import org.koin.dsl.module

/**
 * Domain dependency injection module.
 */
//val domainModule = module {
//
//    // Task Use Cases
//    factory<AddTask> { AddTaskImpl(get(), get()) }
//    factory { CompleteTask(get(), get(), get(), get()) }
//    factory { UncompleteTask(get()) }
//    factory<UpdateTaskStatus> { UpdateTaskStatusImpl(get(), get(), get(), get()) }
//    factory { DeleteTask(get(), get()) }
//    factory<LoadTask> { LoadTaskImpl(get()) }
//    factory<UpdateTask> { UpdateTaskImpl(get(), get()) }
//    factory<UpdateTaskTitle> { UpdateTaskTitleImpl(get(), get(), get()) }
//    factory<UpdateTaskDescription> { UpdateTaskDescriptionImpl(get(), get()) }
//    factory<UpdateTaskCategory> { UpdateTaskCategoryImpl(get(), get()) }
//
//    // Category Use Cases
//    factory<DeleteCategory> { DeleteCategoryImpl(get()) }
//    factory<LoadAllCategories> { LoadAllCategoriesImpl(get()) }
//    factory { LoadCategory(get()) }
//    factory<AddCategory> { AddCategoryImpl(get()) }
//    factory<UpdateCategory> { UpdateCategoryImpl(get()) }
//
//    // Search Use Cases
//    factory<SearchTasksByName> { SearchTasksByNameImpl(get()) }
//
//    // Task With Category Use Cases
//    factory { LoadCompletedTasks(get()) }
//    factory<LoadUncompletedTasks> { LoadUncompletedTasksImpl(get()) }
//
//    // Alarm Use Cases
//    factory<CancelAlarm> { CancelAlarmImpl(get(), get()) }
//    factory { RescheduleFutureAlarms(get(), get(), get(), get()) }
//    factory<ScheduleAlarm> { ScheduleAlarmImpl(get(), get()) }
//    factory { ScheduleNextAlarm(get(), get(), get()) }
//    factory { ShowAlarm(get(), get(), get()) }
//    factory { SnoozeAlarm(get(), get(), get()) }
//    factory<UpdateTaskAsRepeating> { UpdateTaskAsRepeatingImpl(get()) }
//
//    // Tracker Use Cases
//    factory<LoadCompletedTasksByPeriod> { LoadCompletedTasksByPeriodImpl(get()) }
//
//    // Preferences Use Cases
//    factory { UpdateAppTheme(get()) }
//    factory { LoadAppTheme(get()) }
//
//    // Providers
//    factory<CalendarProvider> { CalendarProviderImpl() }
//}
@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun getCompleteTask(
        taskRepository: TaskRepository,
        notificationInteractor: NotificationInteractor,
        alarmInteractor: AlarmInteractor,
        calendarProvider: CalendarProvider
    ): CompleteTask {
        return CompleteTask(
            taskRepository,
            alarmInteractor,
            notificationInteractor,
            calendarProvider
        )
    }


    @Provides
    fun getAddTask(
        taskRepository: TaskRepository,
        glanceInteractor: GlanceInteractor
    ): AddTask {
        return AddTaskImpl(taskRepository, glanceInteractor)
    }


    @Provides
    fun getUnCompleteTask(
        taskRepository: TaskRepository
    ): UncompleteTask {
        return UncompleteTask(taskRepository)
    }

    @Provides
    fun getUpdateTaskStatus(
         taskRepository: TaskRepository,
         glanceInteractor: GlanceInteractor,
         completeTask: CompleteTask,
         uncompleteTask: UncompleteTask
    ): UpdateTaskStatus {
        return UpdateTaskStatusImpl(taskRepository, glanceInteractor,completeTask,uncompleteTask)
    }

    @Provides
    fun getLoadTask(
        taskRepository: TaskRepository
    ): LoadTask {
        return LoadTaskImpl(taskRepository)
    }

    @Provides
    fun getDeleteTask(
        taskRepository: TaskRepository,
        alarmInteractor: AlarmInteractor
    ): DeleteTask {
        return DeleteTask(taskRepository, alarmInteractor)
    }

    @Provides
    fun getUpdatedTask(
        taskRepository: TaskRepository,
        glanceInteractor: GlanceInteractor
    ): UpdateTask {
        return UpdateTaskImpl(taskRepository, glanceInteractor)
    }


    @Provides
    fun getUpdateTaskTitle(
        loadTask: LoadTask,
        updateTask: UpdateTask,
        glanceInteractor: GlanceInteractor
    ): UpdateTaskTitle {
        return UpdateTaskTitleImpl(loadTask, updateTask, glanceInteractor)
    }

    @Provides
    fun getUpdateTaskDescription(
        loadTask: LoadTask,
        updateTask: UpdateTask
    ): UpdateTaskDescription {
        return UpdateTaskDescriptionImpl(loadTask, updateTask)
    }

    @Provides
    fun getUpdateTaskCategory(
        loadTask: LoadTask,
        updateTask: UpdateTask
    ): UpdateTaskCategory {
        return UpdateTaskCategoryImpl(loadTask, updateTask)
    }


    @Provides
    fun getDeleteCategory(
        categoryRepository: CategoryRepository
    ): DeleteCategory {
        return DeleteCategoryImpl(categoryRepository)
    }

    @Provides
    fun getLoadAllCategories(
        categoryRepository: CategoryRepository
    ): LoadAllCategories {
        return LoadAllCategoriesImpl(categoryRepository)
    }

    @Provides
     fun getLoadCategory(
        categoryRepository: CategoryRepository
    ): LoadCategory{
         return LoadCategory(categoryRepository)
     }

    @Provides
    fun getAddCategory(
        categoryRepository: CategoryRepository
    ): AddCategory {
        return AddCategoryImpl(categoryRepository)
    }


    @Provides
    fun getUpdateCategory(
        categoryRepository: CategoryRepository
    ): UpdateCategory {
        return UpdateCategoryImpl(categoryRepository)
    }

    @Provides
    fun getSearchRepository(
        searchRepository: SearchRepository

    ): SearchTasksByName {
        return SearchTasksByNameImpl(searchRepository)
    }

    @Provides
    fun getLoadCompletedTasks(
        repository: TaskWithCategoryRepository
    ): LoadCompletedTasks {
        return LoadCompletedTasks(repository)
    }

    @Provides
    fun getLoadUncompletedTasks(
        repository: TaskWithCategoryRepository
    ): LoadUncompletedTasks {
        return LoadUncompletedTasksImpl(repository)
    }


    @Provides
    fun getCancelAlarm(
        taskRepository: TaskRepository,
        alarmInteractor: AlarmInteractor
    ): CancelAlarm {
        return CancelAlarmImpl(taskRepository, alarmInteractor)
    }


    @Provides
    fun getRescheduleFutureAlarms(
        taskRepository: TaskRepository,
        alarmInteractor: AlarmInteractor,
        calendarProvider: CalendarProvider,
        scheduleNextAlarm: ScheduleNextAlarm
    ): RescheduleFutureAlarms {
        return RescheduleFutureAlarms(
            taskRepository,
            alarmInteractor,
            calendarProvider,
            scheduleNextAlarm
        )
    }

    @Provides
    fun getScheduleAlarm(
        taskRepository: TaskRepository,
        alarmInteractor: AlarmInteractor
    ): ScheduleAlarm {
        return ScheduleAlarmImpl(taskRepository, alarmInteractor)
    }

    @Provides
    fun getSnoozeAlarm(
        calendarProvider: CalendarProvider,
        notificationInteractor: NotificationInteractor,
        alarmInteractor: AlarmInteractor
    ): SnoozeAlarm {
        return SnoozeAlarm(calendarProvider, notificationInteractor, alarmInteractor)
    }

    @Provides
    fun getUpdateTaskAsRepeating(
        taskRepository: TaskRepository
    ): UpdateTaskAsRepeating {
        return UpdateTaskAsRepeatingImpl(taskRepository)
    }

    @Provides
    fun getLoadCompletedTasksByPeriod(
        repository: TaskWithCategoryRepository
    ): LoadCompletedTasksByPeriod {
        return LoadCompletedTasksByPeriodImpl(repository)
    }

    @Provides
    fun getUpdateAppTheme(
        preferencesRepository: PreferencesRepository
    ): UpdateAppTheme {
        return UpdateAppTheme(preferencesRepository)
    }


    @Provides
     fun getLoadAppTheme(
        preferencesRepository: PreferencesRepository
    ): LoadAppTheme{
         return LoadAppTheme(preferencesRepository)
     }

    @Provides
    fun getCalendarProvider(
    ): CalendarProvider {
        return CalendarProviderImpl()
    }
}