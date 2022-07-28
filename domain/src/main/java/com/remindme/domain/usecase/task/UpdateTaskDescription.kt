package com.remindme.domain.usecase.task

import android.content.Context

/**
 * Use case to update a task description.
 */
interface UpdateTaskDescription {

    /**
     * Updates a task description.
     *
     * @param taskId the task id to be updated
     * @param description the description to be set
     */
    suspend operator fun invoke(taskId: Int?, description: String)
}
