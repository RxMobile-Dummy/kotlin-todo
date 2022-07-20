package com.remindme.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

/**
 * [Entity] to represent a category.
 *
 * @property id category id
 * @property name category name
 * @property color category color
 */
@Entity
data class Category @Inject constructor(
    @ColumnInfo(name = "category_id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "category_name") var name: String,
    @ColumnInfo(name = "category_color") var color: String
)