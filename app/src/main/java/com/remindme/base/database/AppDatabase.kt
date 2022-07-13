package com.remindme.base.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.remindme.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

}
