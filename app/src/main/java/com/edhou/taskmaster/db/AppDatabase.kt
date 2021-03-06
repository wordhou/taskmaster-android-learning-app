package com.edhou.taskmaster.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.edhou.taskmaster.models.Task
import com.edhou.taskmaster.models.DatabaseTypeConverters

@Database(entities = arrayOf(Task::class), version = 2, exportSchema = false)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "com.edhou.taskmaster"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
