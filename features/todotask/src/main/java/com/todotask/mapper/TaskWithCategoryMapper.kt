package com.remindme.task.mapper

import javax.inject.Inject
import com.remindme.domain.model.TaskWithCategory as DomainTaskWithCategory
import com.remindme.task.model.TaskWithCategory as ViewTaskWithCategory

/**
 * Maps Task With Category between View and Domain.
 */
class TaskWithCategoryMapper @Inject constructor(
    private val taskMapper: TaskMapper,
    private val categoryMapper: CategoryMapper
) {

    /**
     * Maps Task With Category from Domain to View.
     *
     * @param localTaskList the list of Task With Category to be converted.
     *
     * @return the converted list of Task With Category
     */
    fun toView(localTaskList: List<DomainTaskWithCategory>): List<ViewTaskWithCategory> =
        localTaskList.map { toView(it) }

    private fun toView(localTask: DomainTaskWithCategory): ViewTaskWithCategory =
        ViewTaskWithCategory(
            task = taskMapper.toView(localTask.task),
            category = localTask.category?.let { categoryMapper.toView(it) }
        )
}
