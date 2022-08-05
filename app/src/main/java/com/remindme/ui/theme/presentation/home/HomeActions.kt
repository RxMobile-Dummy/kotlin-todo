package com.remindme.ui.theme.presentation.home

import com.remindme.model.HomeSection
import javax.inject.Inject

internal data class HomeActions @Inject constructor(
    val onTaskClick: (Long?) -> Unit = {},
    val onAboutClick: () -> Unit = {},
    val onTrackerClick: () -> Unit,
    val setCurrentSection: (HomeSection) -> Unit = {}
)
