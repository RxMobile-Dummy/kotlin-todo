package com.remindme.domain.repository

import com.remindme.domain.model.TaskWithCategory
import kotlinx.coroutines.flow.Flow

/**
 * Interface to represent the implementation of Search repository.
 */
interface SearchRepository {

    /**
     * Gets tasks based on the given name.
     *
     * @param query the name to query
     *
     * @return the list of tasks that match the given query
     */
    suspend fun findTaskByName(query: String): Flow<List<TaskWithCategory>>
}
