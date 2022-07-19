package com.remindme.local.provider

import com.remindme.local.dao.CategoryDao
import com.remindme.local.dao.TaskDao
import com.remindme.local.dao.TaskWithCategoryDao
import javax.inject.Inject

/**
 * Repository with the database [androidx.room.Dao]s.
 */
class DaoProvider @Inject constructor(private val database: DatabaseProvider) {

    /**
     * Gets the [TaskDao].
     *
     * @return the [TaskDao]
     */
    fun getTaskDao(): TaskDao =
        database.getInstance().taskDao()

    /**
     * Gets the [TaskWithCategoryDao].
     *
     * @return the [TaskWithCategoryDao]
     */
    fun getTaskWithCategoryDao(): TaskWithCategoryDao =
        database.getInstance().taskWithCategoryDao()

    /**
     * Gets the [CategoryDao].
     *
     * @return the [CategoryDao]
     */
    fun getCategoryDao(): CategoryDao =
        database.getInstance().categoryDao()
}
