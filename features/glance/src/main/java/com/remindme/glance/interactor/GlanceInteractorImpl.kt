package com.remindme.glance.interactor

import android.content.Context
import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.domain.usecase.task.UpdateTaskStatus
import com.remindme.domain.usecase.taskwithcategory.LoadUncompletedTasks
import com.remindme.glance.mapper.TaskMapper
import com.remindme.glance.presentation.TaskListGlanceViewModel
import com.remindme.glance.presentation.TaskListGlanceWidget
import dagger.assisted.Assisted
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GlanceInteractorImpl @Inject constructor() :
    GlanceInteractor {

    /**
     * Updates all the [TaskListGlanceWidget] to load the latest data.
     */
    override suspend fun onTaskListUpdated() {
        TaskListGlanceWidget().loadData()
    }



}
