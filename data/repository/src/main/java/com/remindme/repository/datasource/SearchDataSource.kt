package com.remindme.repository.datasource

import com.remindme.repository.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow

/**
 * Interface to represent the implementation of Search data source.
 */
interface SearchDataSource {

    /**
     * Gets tasks based on the given name.
     *
     * @param query the name to query
     *
     * @return the list of tasks that match the given query
     */
    suspend fun findTaskByName(query: String): Flow<List<TaskWithCategory>>
}
