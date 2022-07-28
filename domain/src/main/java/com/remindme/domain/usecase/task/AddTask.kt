package com.remindme.domain.usecase.task

import android.content.Context
import com.remindme.domain.model.Task
import dagger.Module
import dagger.Provides

/**
 * Use case to add a task from the database.
 */

interface AddTask {

    /**
     * Adds a task.
     *
     * @param task the task to be added
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(task: Task)
}
