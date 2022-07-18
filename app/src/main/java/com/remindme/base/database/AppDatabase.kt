package com.remindme.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.remindme.model.HomeSection
import com.remindme.model.TaskModel

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

}
