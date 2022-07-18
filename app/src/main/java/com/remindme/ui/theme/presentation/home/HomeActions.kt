package com.remindme.presentation.home

import com.remindme.model.HomeSection
import javax.inject.Inject

internal data class HomeActions @Inject constructor(
    val onTaskClick: (Int?) -> Unit = {},
    val onAboutClick: () -> Unit = {},
    val onTrackerClick: () -> Unit,
    val setCurrentSection: (HomeSection) -> Unit = {}
)
