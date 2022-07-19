package com.remindme.local.datasource

import com.remindme.local.mapper.TaskWithCategoryMapper
import com.remindme.local.provider.DaoProvider
import com.remindme.repository.datasource.SearchDataSource
import com.remindme.repository.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchLocalDataSource @Inject constructor(
    daoProvider: DaoProvider,
    private val taskWithCategoryMapper: TaskWithCategoryMapper
) : SearchDataSource {

    private val taskWithCategoryDao = daoProvider.getTaskWithCategoryDao()

    override suspend fun findTaskByName(query: String): Flow<List<TaskWithCategory>> {
        val enclosedQuery = "%$query%"
        val taskList = taskWithCategoryDao.findTaskByName(enclosedQuery)
        return taskList.map { taskWithCategoryMapper.toRepo(it) }
    }
}
