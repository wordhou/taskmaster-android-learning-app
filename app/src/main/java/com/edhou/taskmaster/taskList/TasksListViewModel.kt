package com.edhou.taskmaster.taskList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.edhou.taskmaster.models.Task
import java.lang.IllegalArgumentException

class TasksListViewModel() : ViewModel() {

}

class TasksListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}