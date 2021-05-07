package com.edhou.taskmaster.addTask

import android.content.Context
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.db.TeamsRepository
import com.edhou.taskmaster.models.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
        val tasksRepository: TasksRepository,
        //val teamsRepository: TeamsRepository
        ) : ViewModel() {
    private val _currentlySelectedTeam: MutableLiveData<TeamData> = MutableLiveData()
    val currentlySelectedTeam: LiveData<TeamData>
        get() = _currentlySelectedTeam

    fun selectTeam(teamData: TeamData) {
        if (teamData != null) _currentlySelectedTeam.value = teamData
    }

    fun unselectTeam() {
        _currentlySelectedTeam.value = null
    }

    fun addTask(newTask: TaskData?) {
        TODO()
    }
}
