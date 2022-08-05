package com.remindme.glance.presentation

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.remindme.domain.interactor.GlanceInteractor
//import org.koin.core.component.KoinComponent
//import org.koin.core.component.inject
import javax.inject.Inject

internal class UpdateTaskStatusAction @Inject constructor() : ActionCallback /*KoinComponent*/ {

    @Inject lateinit var viewModel: TaskListGlanceViewModel

    @Inject lateinit var glanceInteractor:   GlanceInteractor


    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val taskId = parameters[TaskIdKey]?.toLong() ?: return
        viewModel.updateTaskAsCompleted(taskId)
        glanceInteractor.onTaskListUpdated()
    }
}

internal val TaskIdKey = ActionParameters.Key<String>("TaskId")
