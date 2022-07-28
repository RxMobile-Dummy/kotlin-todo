package com.remindme.domain.usecase.task

import android.content.Context

/**
 * Use case to update the task category.
 */
interface UpdateTaskCategory {

    /**
     * Updates the task category.
     *
     * @param taskId the task id to be updated
     * @param categoryId the category id to be set
     */
    suspend operator fun invoke(taskId: Int?, categoryId: Long?)
}
