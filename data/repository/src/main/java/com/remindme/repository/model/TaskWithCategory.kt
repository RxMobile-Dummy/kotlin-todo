package com.remindme.repository.model

import javax.inject.Inject

/**
 * Data class to represent a Task with Category.
 *
 * @property task the associated task
 * @property category the associated category
 */
data class TaskWithCategory @Inject constructor(
    val task: Task,
    val category: Category? = null
)
