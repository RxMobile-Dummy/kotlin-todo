package com.remindme.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.remindme.local.converter.AlarmIntervalConverter
import com.remindme.local.converter.DateConverter
import com.remindme.local.dao.CategoryDao
import com.remindme.local.dao.TaskDao
import com.remindme.local.dao.TaskWithCategoryDao
import com.remindme.local.model.Category
import com.remindme.local.model.Task

/**
 * [Task] Database class.
 */
@Database(entities = [Task::class, Category::class], version = 4)
@TypeConverters(DateConverter::class, AlarmIntervalConverter::class)
abstract class TaskDatabase : RoomDatabase() {

    /**
     * Gets the [TaskDao].
     *
     * @return the [TaskDao]
     */
    abstract fun taskDao(): TaskDao

    /**
     * Gets the [TaskWithCategoryDao].
     *
     * @return the [TaskWithCategoryDao]
     */
    abstract fun taskWithCategoryDao(): TaskWithCategoryDao

    /**
     * Gets the [CategoryDao].
     *
     * @return the [CategoryDao]
     */
    abstract fun categoryDao(): CategoryDao
}
