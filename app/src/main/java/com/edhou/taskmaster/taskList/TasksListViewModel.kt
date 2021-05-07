package com.edhou.taskmaster.taskList

import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.db.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
        private val tasksRepository: TasksRepository
) : ViewModel() {
    private val _tasksList: MutableLiveData<List<TaskData>> = MutableLiveData()
    val tasksList: LiveData<List<TaskData>>
        get() = _tasksList
    private var userTeams: Set<TeamData>? = null

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

    private suspend fun updateTasksList() {
        if (userTeams != null) {
            tasksRepository.getTasksListByTeamsFlow(userTeams!!).collect {
                _tasksList.value = it
            }
        } else {
            tasksRepository.getTasksListFlow().collect {
                _tasksList.value = it
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            updateTasksList()
        }
    }

    fun setUserTeams(userTeams: Set<TeamData>) {
        this.userTeams = userTeams
    }
}