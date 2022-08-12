package com.remindme.searchTask.model

import androidx.compose.ui.graphics.Color
import com.remindme.categoryapi.model.Category
import com.todotask.model.Task
import javax.inject.Inject

/**
 * UI representation of Task results.
 */
data class TaskSearchItem @Inject constructor(
    val id: Long? = 0,
    val completed: Boolean,
    val title: String,
    val categoryColor: Color?,
    val isRepeating: Boolean,
    val task: com.remindme.domain.model.Task,
    val category: com.remindme.domain.model.Category? = null
)
