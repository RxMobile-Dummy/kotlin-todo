package com.remindme.repository.model

import javax.inject.Inject

/**
 * Data class to represent a Category.
 *
 * @property id category id
 * @property name category name
 * @property color category color
 */
data class Category @Inject constructor(
    val id: Long = 0,
    val name: String,
    val color: String
)
