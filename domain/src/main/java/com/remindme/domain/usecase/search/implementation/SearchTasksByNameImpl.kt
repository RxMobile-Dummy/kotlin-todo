package com.remindme.domain.usecase.search.implementation

import com.remindme.domain.model.TaskWithCategory
import com.remindme.domain.repository.SearchRepository
import com.remindme.domain.usecase.search.SearchTasksByName
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchTasksByNameImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchTasksByName {

    /**
     * Gets tasks based on the given name.
     *
     * @param query the name to query
     *
     * @return the list of tasks that match the given query
     */
    override suspend operator fun invoke(query: String): Flow<List<TaskWithCategory>> =
        searchRepository.findTaskByName(query)
}
