package com.remindme.domain.usecase.taskwithcategory.implementation

import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.repository.TaskWithCategoryRepository
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadUncompletedTasksImpl @Inject constructor(
    private val repository: TaskWithCategoryRepository
) : LoadUncompletedTasks {

    override fun invoke(categoryId: Long?): Flow<List<TaskWithCategory>> =
        if (categoryId == null) {
            repository.findAllTasksWithCategory()
                .map { list -> list.filterNot { item -> item.task.completed } }
        } else {
            repository.findAllTasksWithCategoryId(categoryId)
        }.map { list -> list.filterNot { item -> item.task.completed } }
}
