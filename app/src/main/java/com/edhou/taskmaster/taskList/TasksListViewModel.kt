package com.edhou.taskmaster.taskList

import android.content.Context
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
        val tasksRepository: TasksRepository) : ViewModel() {
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