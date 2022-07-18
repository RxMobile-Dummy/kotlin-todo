package com.remindme.search.model

import androidx.compose.ui.graphics.Color
import javax.inject.Inject

/**
 * UI representation of Task results.
 */
data class TaskSearchItem(
    val id: Int? = 0,
    val completed: Boolean,
    val title: String,
    val categoryColor: Color?,
    val isRepeating: Boolean
)
