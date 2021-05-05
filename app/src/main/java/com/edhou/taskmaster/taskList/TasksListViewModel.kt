package com.edhou.taskmaster.taskList

import android.content.Context
import androidx.lifecycle.*
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.models.Task
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TasksListViewModel(val tasksRepository: TasksRepository) : ViewModel() {
    private val _tasksList: MutableLiveData<List<Task>> = MutableLiveData()
    val tasksList: LiveData<List<Task>>
        get() = _tasksList

    init {
        viewModelScope.launch {
            tasksRepository.getTasksList().collect {
                _tasksList.value = it
            }
        }
    }
}

class TasksListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TasksListViewModel(
                    (context.applicationContext as TaskmasterApplication).tasksRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}