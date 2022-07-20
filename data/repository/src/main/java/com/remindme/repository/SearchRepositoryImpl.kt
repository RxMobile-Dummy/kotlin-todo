package com.remindme.repository

import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.repository.SearchRepository
import com.remindme.repository.datasource.SearchDataSource
import com.remindme.repository.mapper.TaskWithCategoryMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource,
    private val mapper: TaskWithCategoryMapper
) : SearchRepository {

    override suspend fun findTaskByName(query: String): Flow<List<TaskWithCategory>> =
        searchDataSource.findTaskByName(query).map { mapper.toDomain(it) }
}
