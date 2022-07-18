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
import com.remindme.domain.usecase.alarm.CancelAlarm
import com.remindme.domain.usecase.alarm.RescheduleFutureAlarms
import com.remindme.domain.usecase.alarm.ScheduleAlarm
import com.remindme.domain.usecase.alarm.ScheduleNextAlarm
import com.remindme.domain.usecase.alarm.ShowAlarm
import com.remindme.domain.usecase.alarm.SnoozeAlarm
import com.remindme.domain.usecase.alarm.UpdateTaskAsRepeating
import com.remindme.domain.usecase.alarm.implementation.CancelAlarmImpl
import com.remindme.domain.usecase.alarm.implementation.ScheduleAlarmImpl
import com.remindme.domain.usecase.alarm.implementation.UpdateTaskAsRepeatingImpl
import com.remindme.domain.usecase.category.AddCategory
import com.remindme.domain.usecase.category.DeleteCategory
import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.category.LoadCategory
import com.remindme.domain.usecase.category.UpdateCategory
import com.remindme.domain.usecase.category.implementation.AddCategoryImpl
import com.remindme.domain.usecase.category.implementation.DeleteCategoryImpl
import com.remindme.domain.usecase.category.implementation.LoadAllCategoriesImpl
import com.remindme.domain.usecase.category.implementation.UpdateCategoryImpl
import com.remindme.domain.usecase.preferences.LoadAppTheme
import com.remindme.domain.usecase.preferences.PreferencesRepository
import com.remindme.domain.usecase.preferences.UpdateAppTheme
import com.remindme.domain.usecase.search.SearchTasksByName
import com.remindme.domain.usecase.search.implementation.SearchTasksByNameImpl
import com.remindme.domain.usecase.task.AddTask
import com.remindme.domain.usecase.task.CompleteTask
import com.remindme.domain.usecase.task.DeleteTask
import com.remindme.domain.usecase.task.LoadTask
import com.remindme.domain.usecase.task.UncompleteTask
import com.remindme.domain.usecase.task.UpdateTask
import com.remindme.domain.usecase.task.UpdateTaskCategory
import com.remindme.domain.usecase.task.UpdateTaskDescription
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.task.UpdateTaskTitle
import com.remindme.domain.usecase.task.implementation.AddTaskImpl
import com.remindme.domain.usecase.task.implementation.LoadTaskImpl
import com.remindme.domain.usecase.task.implementation.UpdateTaskCategoryImpl
import com.remindme.domain.usecase.task.implementation.UpdateTaskDescriptionImpl
import com.remindme.domain.usecase.task.implementation.UpdateTaskImpl
import com.remindme.domain.usecase.task.implementation.UpdateTaskStatusImpl
import com.remindme.domain.usecase.task.implementation.UpdateTaskTitleImpl
import com.remindme.domain.usecase.taskwithcategory.LoadCompletedTasks
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.domain.usecase.taskwithcategory.implementation.LoadUncompletedTasksImpl
import com.remindme.domain.usecase.tracker.LoadCompletedTasksByPeriod
import com.remindme.domain.usecase.tracker.implementation.LoadCompletedTasksByPeriodImpl
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
//@Module
//@InstallIn(SingletonComponent::class)
//abstract  class  DomainModule {
//
//    @Binds
//    abstract fun getAddTask(
//        addTaskImpl: AddTaskImpl
//    ): AddTask
//
//
//
//    @Binds
//    abstract fun getCategoryRepository(
//    ): CompleteTask
//
//
//    @Binds
//    abstract fun getUncompleteTask(
//    ): UncompleteTask
//
//
//    @Binds
//    abstract fun getUpdateTaskStatus(
//        ): UpdateTaskStatus
//
//    @Binds
//    abstract fun getLoadTask(
//        loadTaskImpl: LoadTaskImpl
//    ): LoadTask
//
//    @Binds
//    abstract fun getDeleteTask(
//    ): DeleteTask
//
//    @Binds
//    abstract fun getUpdatedTask(
//        updateTaskImpl: UpdateTaskImpl
//    ): UpdateTask
//
//
//    @Binds
//    abstract fun getUpdateTaskTitle(
//        loadTask: LoadTask,
//        updateTask: UpdateTask,
//        glanceInteractor: GlanceInteractor,
//        updateTaskTitleImpl: UpdateTaskTitleImpl
//    ): UpdateTaskTitle
//
//
//
//    @Binds
//    abstract fun getUpdateTaskDescription(
//        loadTask: LoadTask,
//        updateTask: UpdateTask,
//        updateTaskDescriptionImpl: UpdateTaskDescriptionImpl
//        ): UpdateTaskDescription
//
//    @Binds
//    abstract fun getUpdateTaskCategory(
//        loadTask: LoadTask,
//        updateTask: UpdateTask,
//        updateTaskCategoryImpl: UpdateTaskCategoryImpl
//
//        ): UpdateTaskCategory
//
//    //usecases
//
//    @Binds
//      abstract  fun getDeleteCategory(
//        categoryRepository: CategoryRepository,
//        deleteCategoryImpl: DeleteCategoryImpl
//    ): DeleteCategory
//
//    @Binds
//
//    abstract fun getLoadAllCategories(
//        categoryRepository: CategoryRepository,
//        loadAllCategoriesImpl: LoadAllCategoriesImpl
//    ): LoadAllCategories
//
//    @Binds
//    abstract fun getLoadCategory(
//        categoryRepository: CategoryRepository
//    ): LoadCategory
//
//    @Binds
//    abstract fun getAddCategory(
//        categoryRepository: CategoryRepository,
//        addCategoryImpl: AddCategoryImpl
//    ): AddCategory
//
//    @Binds
//    abstract fun getAddCategory(
//        addCategoryImpl: AddCategoryImpl
//    ): AddCategory
//
//
//    @Binds
//    abstract fun getUpdateCategory(
//        categoryRepository: CategoryRepository,
//        updateCategoryImpl: UpdateCategoryImpl
//    ): UpdateCategory
//
//    @Binds
//    abstract fun getSearchRepository(
//        searchRepository: SearchRepository,
//        searchTasksByNameImpl: SearchTasksByNameImpl
//    ): SearchTasksByName
//
//    @Binds
//    abstract  fun getLoadCompletedTasks(
//        taskWithCategoryRepository: TaskWithCategoryRepository
//    ): LoadCompletedTasks
//
//    @Binds
//    abstract  fun getLoadUncompletedTasks(
//        taskWithCategoryRepository: TaskWithCategoryRepository,
//    loadUncompletedTasksImpl: LoadUncompletedTasksImpl
//    ): LoadUncompletedTasks<TaskWithCategoryRepository>
//
//    @Binds
//    abstract fun getCancelAlarm(
//        cancelAlarmImpl: CancelAlarmImpl
//    ): CancelAlarm
//
//
//    @Binds
//    abstract fun getRescheduleFutureAlarms(
//    ): RescheduleFutureAlarms
//
//    @Binds
//    abstract fun getScheduleAlarm(
//        taskRepository: TaskRepository,
//        alarmInteractor: AlarmInteractor,
//        scheduleAlarmImpl: ScheduleAlarmImpl
//    ): ScheduleAlarm
//
//    @Binds
//    abstract fun getSnoozeAlarm(
//        calendarProvider: CalendarProvider,
//        notificationInteractor: NotificationInteractor,
//        alarmInteractor: AlarmInteractor,
//    ): SnoozeAlarm
//
//    @Binds
//    abstract fun getUpdateTaskAsRepeating(
//        taskRepository: TaskRepository
//    ): UpdateTaskAsRepeating
//
//    @Binds
//    abstract fun getLoadCompletedTasksByPeriod(
//        loadCompletedTasksByPeriodImpl: LoadCompletedTasksByPeriodImpl
//    ): LoadCompletedTasksByPeriod
//
//    @Binds
//    abstract fun getUpdateAppTheme(
//        preferencesRepository: PreferencesRepository
//    ): UpdateAppTheme
//
//    @Binds
//    abstract fun getLoadAppTheme(
//        preferencesRepository: PreferencesRepository
//    ): LoadAppTheme
//
//    @Binds
//    abstract fun getCalendarProvider(
//        calendarProviderImpl: CalendarProviderImpl
//    ): CalendarProvider
//}