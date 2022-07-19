package com.remindme.glance.mapper

import com.remindme.domain.model.TaskWithCategory
import com.remindme.glance.model.Task

 class TaskMapper {

    fun toView(localTaskList: List<TaskWithCategory>): List<Task> =
        localTaskList.map { toView(it) }

    private fun toView(taskWithCategory: TaskWithCategory): Task =
        Task(id = taskWithCategory.task.id, title = taskWithCategory.task.title)
}
