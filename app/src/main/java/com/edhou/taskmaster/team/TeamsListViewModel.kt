package com.edhou.taskmaster.team

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.db.TeamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TeamsListViewModel(val teamsRepository: TeamsRepository) : ViewModel() {
    private val _teamsList: MutableLiveData<List<TeamData>> = MutableLiveData()
    val teams: LiveData<List<TeamData>>
        get() = _teamsList

    init {
        viewModelScope.launch {
            updateTeamsList()
        }
    }

    suspend fun updateTeamsList() {
        Log.i("TeamsListViewModel", "Getting teams list")
        Log.i("TeamsListViewModel", "Thread: ${Thread.currentThread().name}")
        val teamsList = teamsRepository.getTeamsList()
        _teamsList.value = teamsList
        Log.i("TeamsListViewModel", "Teams list: ${teamsList.map { it.name }}")
    }

    fun insert(newTeam: TeamData) {
        Log.i("TeamsListViewModel", "insert new Team $newTeam")
        viewModelScope.launch {
            teamsRepository.insert(newTeam)
            val l = _teamsList.value?.toMutableList()
            l?.apply {
                add(newTeam)
                _teamsList.value = l
            }
            updateTeamsList()
        }

    }

    fun update() {
        viewModelScope.launch {
            updateTeamsList()
        }
    }
}

class TeamsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TeamsListViewModel(
                    (context.applicationContext as TaskmasterApplication).teamsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}