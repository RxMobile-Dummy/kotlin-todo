package com.remindme.categoryapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

/**
 * Data class to represent a View Category.
 *
 * @property id category id
 * @property name category name
 * @property color category color
 */
@Parcelize
data class Category @Inject constructor(
    val id: Long = 0,
    val name: String,
    val color: Int
) : Parcelable
