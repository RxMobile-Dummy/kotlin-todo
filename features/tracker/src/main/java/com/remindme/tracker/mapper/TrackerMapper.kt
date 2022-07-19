package com.remindme.tracker.mapper

import android.graphics.Color
import com.remindme.domain.model.TaskWithCategory
import com.remindme.tracker.model.Tracker

/**
 * Maps Tracker between View and Domain.
 */
class TrackerMapper {

    /**
     * Maps from a grouped [Map] of [List<ViewData.TaskWithCategory>] to [Tracker].
     *
     * @param list list to be mapped
     *
     * @return the converted object
     */
    internal fun toTracker(list: List<TaskWithCategory>): Tracker.Info {
        val count = list.count()
        val categories = list.groupBy { task -> task.category?.id }.map { toCategory(it, count) }
        return Tracker.Info(categories)
    }

    private fun toCategory(
        map: Map.Entry<Long?, List<TaskWithCategory>>,
        totalCount: Int
    ): Tracker.CategoryInfo {
        val first = map.value.first()
        val taskCount = map.value.size
        return Tracker.CategoryInfo(
            name = first.category?.name,
            color = first.category?.color?.let { color -> Color.parseColor(color) },
            taskCount = taskCount,
            percentage = taskCount.toFloat() / totalCount
        )
    }
}
