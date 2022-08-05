package com.remindme.alarm.mapper

import javax.inject.Inject
import com.remindme.alarm.model.Task as ViewTask
import com.remindme.domain.model.Task as DomainTask

/**
 * Maps Tasks between View and Domain.
 */
class TaskMapper @Inject constructor() {

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
