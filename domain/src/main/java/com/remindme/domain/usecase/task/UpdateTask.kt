package com.remindme.domain.usecase.task

import com.remindme.domain.model.Task

/**
 * Use case to update a task from the database.
 */
interface UpdateTask {

    /**
     * Updates a task.
     *
     * @param task the task to be updated
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(task: Task)
}
