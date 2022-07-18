package com.remindme.domain.usecase.taskwithcategory

import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.repository.TaskWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case to get all completed tasks from the database.
 */
class LoadCompletedTasks @Inject constructor(private val repository: TaskWithCategoryRepository) {

    /**
     * Gets all completed tasks.
     *
     * @return observable to be subscribe
     */
    operator fun invoke(): Flow<List<TaskWithCategory>> =
        repository.findAllTasksWithCategory()
            .map { list -> list.filter { item -> item.task.completed } }
}
