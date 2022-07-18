package com.remindme.tracker.presentation

import androidx.lifecycle.ViewModel
import com.remindme.domain.usecase.tracker.LoadCompletedTasksByPeriod
import com.remindme.tracker.mapper.TrackerMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    private val loadCompletedTasksByPeriod: LoadCompletedTasksByPeriod,
    private val trackerMapper: TrackerMapper
) : ViewModel() {

    fun loadTracker(): Flow<TrackerViewState> = flow {
        loadCompletedTasksByPeriod()
            .map { task -> trackerMapper.toTracker(task) }
            .catch { error -> emit(TrackerViewState.Error(error)) }
            .collect { trackerInfo ->
                val state = if (trackerInfo.categoryInfoList.isNotEmpty()) {
                    TrackerViewState.Loaded(trackerInfo)
                } else {
                    TrackerViewState.Empty
                }
                emit(state)
            }
    }
}
