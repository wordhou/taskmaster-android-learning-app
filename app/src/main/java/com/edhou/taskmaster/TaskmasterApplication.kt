package com.edhou.taskmaster

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.edhou.taskmaster.db.AppDatabase
import com.edhou.taskmaster.db.TasksRepository


class TaskmasterApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { TasksRepository(database.tasksDao()) }
    override fun onCreate() {
        super.onCreate()
        try {
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", e)
        }
    }
}