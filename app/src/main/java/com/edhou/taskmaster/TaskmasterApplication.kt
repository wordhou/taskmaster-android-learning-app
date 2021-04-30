package com.edhou.taskmaster

import android.app.Application
import com.edhou.taskmaster.db.AppDatabase
import com.edhou.taskmaster.db.TasksRepository

class TaskmasterApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { TasksRepository(database.tasksDao()) }
}