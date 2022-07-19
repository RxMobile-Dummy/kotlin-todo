package com.remindme.repository

import com.remindme.domain.model.Task
import com.remindme.domain.repository.TaskRepository
import com.remindme.repository.datasource.TaskDataSource
import com.remindme.repository.mapper.TaskMapper
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDataSource: TaskDataSource,
    private val taskMapper: TaskMapper
) : TaskRepository {
    override suspend fun insertTask(task: Task) =
        taskDataSource.insertTask(taskMapper.toRepo(task))

    override suspend fun updateTask(task: Task) =
        taskDataSource.updateTask(taskMapper.toRepo(task))

    override suspend fun deleteTask(task: Task) =
        taskDataSource.deleteTask(taskMapper.toRepo(task))

    override suspend fun cleanTable() =
        taskDataSource.cleanTable()

    override suspend fun findAllTasksWithDueDate(): List<Task> =
        taskDataSource.findAllTasksWithDueDate().map { taskMapper.toDomain(it) }

    override suspend fun findTaskById(taskId: Int?): Task? =
        taskDataSource.findTaskById(taskId)?.let { taskMapper.toDomain(it) }
}
