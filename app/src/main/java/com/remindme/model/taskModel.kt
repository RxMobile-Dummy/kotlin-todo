package com.remindme.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskModel (
    @PrimaryKey
    val id:Int=0,
    val completed: Boolean = false,
    val title: String);