package com.edhou.taskmaster.taskDetail

import android.content.Context
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.models.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(val tasksRepository: TasksRepository) : ViewModel() {
    private var taskId: String = ""
    val _task: MutableLiveData<TaskData> = MutableLiveData()
    val task: LiveData<TaskData>
        get() = _task

    fun setTaskId(id: String) {
        if (id != taskId) {
            tasksRepository.findByIdFlow(id).onEach { _task.value = it }.launchIn(viewModelScope)
            taskId = id
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.delete(task.value!!)
        }
    }
}
