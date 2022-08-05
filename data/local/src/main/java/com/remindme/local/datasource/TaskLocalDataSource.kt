package com.remindme.local.datasource

import com.remindme.local.mapper.TaskMapper
import com.remindme.local.provider.DaoProvider
import com.remindme.repository.datasource.TaskDataSource
import com.remindme.repository.model.Task
import javax.inject.Inject

/**
 * Local implementation of [TaskDataSource].
 */
class TaskLocalDataSource @Inject constructor(daoProvider: DaoProvider, private val taskMapper: TaskMapper) :
    TaskDataSource {

    private val taskDao = daoProvider.getTaskDao()

    override suspend fun insertTask(task: Task) =
        taskDao.insertTask(taskMapper.toLocal(task))

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(taskMapper.toLocal(task))

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(taskMapper.toLocal(task))

    override suspend fun cleanTable() =
        taskDao.cleanTable()

    override suspend fun findAllTasksWithDueDate(): List<Task> =
        taskDao.findAllTasksWithDueDate().map { taskMapper.toRepo(it) }

    override suspend fun findTaskById(taskId: Long?): Task? =
        taskDao.getTaskById(taskId)?.let { taskMapper.toRepo(it) }
}
