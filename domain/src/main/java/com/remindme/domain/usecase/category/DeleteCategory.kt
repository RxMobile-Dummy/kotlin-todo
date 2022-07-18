package com.remindme.domain.usecase.category

import com.remindme.domain.model.Category
import dagger.Module
import dagger.Provides

/**
 * Use case to delete a category from the database.
 */

interface DeleteCategory {

    /**
     * Deletes a category.
     *
     * @param category category to be deleted
     *
     * @return observable to be subscribe
     */
    suspend operator fun invoke(category: Category)
}
