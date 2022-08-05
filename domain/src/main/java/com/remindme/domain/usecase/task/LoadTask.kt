package com.remindme.domain.usecase.task

import com.remindme.domain.model.Task

/**
 * Use case to get a task from the database.
 */
interface LoadTask {
    /**
     * Gets a task.
     *
     * @param taskId the task id
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(taskId: Long?): Task?
}
