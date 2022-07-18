package com.remindme.domain.usecase.category

import com.remindme.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * Use case to load all categories from the database.
 */
interface LoadAllCategories {
    /**
     * Loads all categories.
     *
     * @return a mutable list of all categories
     */
    operator fun invoke(): Flow<List<Category>>
}
