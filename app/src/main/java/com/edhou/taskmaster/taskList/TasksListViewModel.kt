package com.edhou.taskmaster.taskList

import android.content.Context
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TasksListViewModel(val tasksRepository: TasksRepository) : ViewModel() {
    private val _tasksList: MutableLiveData<List<TaskData>> = MutableLiveData()
    val tasksList: LiveData<List<TaskData>>
        get() = _tasksList

    init {
        viewModelScope.launch {
            updateTasksList()
        }
    }

    /**
     * Removes a task from the list.
     */
    fun delete(currentTaskId: String?) {
        if (currentTaskId != null) {
            _tasksList.value = _tasksList.value?.filter({ it.id != currentTaskId })
        }
        viewModelScope.launch {
            updateTasksList()
        }
    }

    suspend fun updateTasksList() {
            tasksRepository.getTasksListFlow().collect {
                _tasksList.value = it
        }
    }

    fun update() {
        viewModelScope.launch {
            updateTasksList()
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