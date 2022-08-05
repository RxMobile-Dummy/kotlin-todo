package com.todotask.presentation.detail

import com.todotask.model.AlarmInterval
import com.todotask.presentation.detail.main.CategoryId
import java.util.Calendar

data class TaskDetailActions(
    val onTitleChange: (String) -> Unit = {},
    val onDescriptionChange: (String) -> Unit = {},
    val onCategoryChange: (CategoryId) -> Unit = {},
    val onAlarmUpdate: (Calendar?) -> Unit = {},
    val onIntervalSelect: (AlarmInterval) -> Unit = {},
    val hasAlarmPermission: () -> Boolean = { false },
    val onUpPress: () -> Unit = {}
)
