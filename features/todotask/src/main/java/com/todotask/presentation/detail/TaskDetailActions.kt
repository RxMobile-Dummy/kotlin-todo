package com.remindme.task.presentation.detail

import com.remindme.task.model.AlarmInterval
import com.remindme.task.presentation.detail.main.CategoryId
import java.util.Calendar
import javax.inject.Inject

internal data class TaskDetailActions(
    val onTitleChange: (String) -> Unit = {},
    val onDescriptionChange: (String) -> Unit = {},
    val onCategoryChange: (CategoryId) -> Unit = {},
    val onAlarmUpdate: (Calendar?) -> Unit = {},
    val onIntervalSelect: (AlarmInterval) -> Unit = {},
    val hasAlarmPermission: () -> Boolean = { false },
    val onUpPress: () -> Unit = {}
)
