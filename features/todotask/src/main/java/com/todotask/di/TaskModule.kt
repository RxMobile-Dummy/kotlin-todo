package com.todotask.di

import android.content.Context
import com.remindme.domain.usecase.alarm.CancelAlarm
import com.remindme.domain.usecase.alarm.ScheduleAlarm
import com.remindme.domain.usecase.alarm.UpdateTaskAsRepeating
import com.remindme.domain.usecase.category.LoadAllCategories
import com.remindme.domain.usecase.task.*
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.task.presentation.add.AddTaskViewModel
import com.remindme.task.presentation.list.TaskListViewModel
import com.todotask.mapper.AlarmIntervalMapper
import com.todotask.mapper.CategoryMapper
import com.todotask.mapper.TaskMapper
import com.todotask.mapper.TaskWithCategoryMapper
import com.todotask.presentation.detail.alarm.TaskAlarmViewModel
import com.todotask.presentation.detail.main.TaskDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
///**
// * Task dependency injection module.
// */
////val taskModule = module {
////
////    // Presentation
////    viewModel { TaskListViewModel(get(), get(), get()) }
////    viewModel { TaskDetailViewModel(get(), get(), get(), get(), get()) }
////    viewModel { TaskAlarmViewModel(get(), get(), get(), get()) }
////    viewModel { AddTaskViewModel(get()) }
////
////    // Mappers
////    factory { AlarmIntervalMapper() }
////    factory { TaskMapper(get()) }
////    factory { TaskWithCategoryMapper(get(), get()) }
////    factory { CategoryMapper() }
////}
@Module
@InstallIn(SingletonComponent::class)
class TaskModule {

    @Provides
    fun getTaskListViewModel(
        loadUncompletedTasks: LoadUncompletedTasks,
        updateTaskStatus: UpdateTaskStatus,
        taskWithCategoryMapper: TaskWithCategoryMapper
    ): TaskListViewModel {
        return TaskListViewModel(loadUncompletedTasks, updateTaskStatus,taskWithCategoryMapper)
    }
    @Provides
    fun getTaskDetailViewModel(
        loadTaskUseCase: LoadTask,
        updateTaskTitle: UpdateTaskTitle,
        updateTaskDescription: UpdateTaskDescription,
        updateTaskCategory: UpdateTaskCategory,
        taskMapper: TaskMapper
    ): TaskDetailViewModel {
        return TaskDetailViewModel(loadTaskUseCase, updateTaskTitle,updateTaskDescription,updateTaskCategory,taskMapper)
    }
    @Provides
    fun getTaskAlarmViewModel(
        scheduleAlarm: ScheduleAlarm,
        updateTaskAsRepeating: UpdateTaskAsRepeating,
        cancelAlarm: CancelAlarm,
        alarmIntervalMapper: AlarmIntervalMapper,
    ): TaskAlarmViewModel {
        return TaskAlarmViewModel(scheduleAlarm, updateTaskAsRepeating,cancelAlarm,alarmIntervalMapper)
    }
    @Provides
    fun getAddTaskViewModel(
        addTaskUseCase: AddTask,
    ): AddTaskViewModel {
        return AddTaskViewModel(addTaskUseCase)
    }

    @Provides
    fun alarmIntervalMapper(
    ): AlarmIntervalMapper {
        return AlarmIntervalMapper()
    }

    @Provides
    fun taskMapper(
        alarmIntervalMapper: AlarmIntervalMapper
    ): TaskMapper {
        return TaskMapper(alarmIntervalMapper)
    }
    @Provides
    fun taskWithCategoryMapper(
        taskMapper: TaskMapper,
        categoryMapper: CategoryMapper
    ): TaskWithCategoryMapper {
        return TaskWithCategoryMapper(taskMapper,categoryMapper)
    }
    @Provides
    fun categoryMapper(
    ): CategoryMapper {
        return CategoryMapper()
    }
}