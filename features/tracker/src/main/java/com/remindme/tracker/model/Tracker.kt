package com.remindme.tracker.model

import javax.inject.Inject

/**
 * UI representation of Tracker.
 */
internal sealed class Tracker {

    /**
     * UI Representation of the Tracker information.
     */
    internal data class Info @Inject constructor(val categoryInfoList: List<CategoryInfo>)

    /**
     * UI representation of each item of Tracker information.
     */
    internal data class CategoryInfo(
        val name: String?,
        val color: Int?,
        val taskCount: Int,
        val percentage: Float
    )
}
