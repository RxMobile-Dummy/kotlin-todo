package com.remindme.domain.usecase.task.implementation

import android.content.Context
import com.remindme.domain.usecase.task.LoadTask
import com.remindme.domain.usecase.task.UpdateTask
import com.remindme.domain.usecase.task.UpdateTaskCategory
import javax.inject.Inject

class UpdateTaskCategoryImpl @Inject constructor(
    private val loadTask: LoadTask,
    private val updateTask: UpdateTask
) : UpdateTaskCategory {

    override suspend fun invoke(taskId: Long?, categoryId: Long?) {
        val task = loadTask(taskId) ?: return
        val updatedTask = task.copy(categoryId = categoryId)
        updateTask(updatedTask)
    }
}
