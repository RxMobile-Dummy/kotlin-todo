package com.remindme.glance.presentation

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.glance.mapper.TaskMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Future
import javax.inject.Inject

internal class TaskListGlanceReceiver @Inject constructor(): GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = TaskListGlanceWidget().apply {
        loadData()
    }
}
