package com.remindme.domain.usecase.task

import android.content.Context

/**
 * Use case to update a task title.
 */
interface UpdateTaskTitle {

    /**
     * Updates a task title.
     *
     * @param taskId the task id to be updated
     * @param title the title to be set
     */
    suspend operator fun invoke(taskId: Long?, title: String)
}
