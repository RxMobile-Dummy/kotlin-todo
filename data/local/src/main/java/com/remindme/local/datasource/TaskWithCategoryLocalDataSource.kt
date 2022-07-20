package com.remindme.local.datasource

import com.remindme.local.mapper.TaskWithCategoryMapper
import com.remindme.local.provider.DaoProvider
import com.remindme.repository.datasource.TaskWithCategoryDataSource
import com.remindme.repository.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Local implementation of [TaskWithCategoryDataSource].
 */
class TaskWithCategoryLocalDataSource @Inject constructor(
    daoProvider: DaoProvider,
    private val mapper: TaskWithCategoryMapper
) : TaskWithCategoryDataSource {

    private val taskWithCategoryDao = daoProvider.getTaskWithCategoryDao()

    override fun findAllTasksWithCategory(): Flow<List<TaskWithCategory>> =
        taskWithCategoryDao.findAllTasksWithCategory().map { mapper.toRepo(it) }

    override fun findAllTasksWithCategoryId(categoryId: Long): Flow<List<TaskWithCategory>> =
        taskWithCategoryDao.findAllTasksWithCategoryId(categoryId).map { mapper.toRepo(it) }
}
