package com.remindme.repository

import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.repository.TaskWithCategoryRepository
import com.remindme.repository.datasource.TaskWithCategoryDataSource
import com.remindme.repository.mapper.TaskWithCategoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskWithCategoryRepositoryImpl @Inject constructor(
    private val dataSource: TaskWithCategoryDataSource,
    private val mapper: TaskWithCategoryMapper
) : TaskWithCategoryRepository {

    override fun findAllTasksWithCategory(): Flow<List<TaskWithCategory>> =
        dataSource.findAllTasksWithCategory().map { mapper.toDomain(it) }

    override fun findAllTasksWithCategoryId(categoryId: Long): Flow<List<TaskWithCategory>> =
        dataSource.findAllTasksWithCategoryId(categoryId).map { mapper.toDomain(it) }
}
