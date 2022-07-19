package com.remindme.glance.interactor

import com.remindme.domain.interactor.GlanceInteractor
import com.remindme.glance.presentation.TaskListGlanceWidget

 class GlanceInteractorImpl : GlanceInteractor {

    /**
     * Updates all the [TaskListGlanceWidget] to load the latest data.
     */
    override suspend fun onTaskListUpdated() {
        TaskListGlanceWidget().loadData()
    }
}
