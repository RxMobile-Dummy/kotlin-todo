package com.todotask.di

import com.remindme.domain.usecase.alarm.CancelAlarm
import com.remindme.domain.usecase.alarm.ScheduleAlarm
import com.remindme.domain.usecase.alarm.UpdateTaskAsRepeating
import com.remindme.domain.usecase.task.*
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.task.presentation.add.AddTaskViewModel
import com.todotask.presentation.list.TaskListViewModel
import com.todotask.mapper.AlarmIntervalMapper
import com.todotask.mapper.CategoryMapper
import com.todotask.mapper.TaskMapper
import com.todotask.mapper.TaskWithCategoryMapper
import com.todotask.presentation.detail.alarm.TaskAlarmViewModel
import com.todotask.presentation.detail.main.TaskDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//
//**
// * Task dependency injection module.
// */
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