package com.remindme.task.mapper

import javax.inject.Inject
import com.remindme.domain.model.Task as DomainTask
import com.remindme.task.model.Task as ViewTask

/**
 * Maps Tasks between Domain and View.
 */
class TaskMapper @Inject constructor(private val alarmIntervalMapper: AlarmIntervalMapper) {

    /**
     * Maps Task from Domain to View.
     *
     * @param domainTask the Task to be converted.
     *
     * @return the converted Task
     */
    fun toView(domainTask: DomainTask): ViewTask =
        ViewTask(
            id = domainTask.id,
            completed = domainTask.completed,
            title = domainTask.title,
            description = domainTask.description,
            dueDate = domainTask.dueDate,
            categoryId = domainTask.categoryId,
            creationDate = domainTask.creationDate,
            completedDate = domainTask.completedDate,
            isRepeating = domainTask.isRepeating,
            alarmInterval = alarmIntervalMapper.toViewData(domainTask.alarmInterval)
        )
}
