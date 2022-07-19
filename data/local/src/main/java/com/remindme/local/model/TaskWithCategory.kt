package com.remindme.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import javax.inject.Inject

/**
 * [Entity] to represent a wrapper of [Task] and [Category].
 *
 * @property task the associated task
 * @property category the associated category
 */
data class TaskWithCategory @Inject constructor(
    @Embedded val task: Task,
    @Embedded val category: Category? = null
)
