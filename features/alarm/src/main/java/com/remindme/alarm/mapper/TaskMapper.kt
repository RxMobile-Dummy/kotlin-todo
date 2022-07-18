package com.remindme.alarm.mapper

import com.remindme.alarm.model.Task as ViewTask
import com.remindme.domain.model.Task as DomainTask

/**
 * Maps Tasks between View and Domain.
 */
class TaskMapper {

    /**
     * Maps Task from Domain to View.
     *
     * @param domainTask the Task to be converted.
     *
     * @return the converted Task
     */
    fun fromDomain(domainTask: DomainTask): ViewTask =
        ViewTask(
            id = domainTask.id,
            title = domainTask.title,
            dueDate = domainTask.dueDate,
            isCompleted = domainTask.completed
        )
}
