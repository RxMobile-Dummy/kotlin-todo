package com.remindme.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.remindme.R

/**
 * Enum to represent the sections available in the bottom app bar.
 *
 * @property title title to be shown in top app bar.
 * @property icon icon to be shown in the bottom app bar
 */
enum class HomeSection(
    @StringRes val title: Int,
    val icon: ImageVector
)
{
    Tasks("Tasks".toInt(), Icons.Outlined.Check),
    Search("Category".toInt(), Icons.Outlined.Search),
    Categories("More".toInt(), Icons.Outlined.Bookmark),
    Settings("Search".toInt(), Icons.Outlined.MoreHoriz)
}