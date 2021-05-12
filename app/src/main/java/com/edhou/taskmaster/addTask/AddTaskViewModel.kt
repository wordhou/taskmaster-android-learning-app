package com.edhou.taskmaster.addTask

import android.util.Log
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.taskDetail.TaskDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
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

    fun uploadPhoto(inputStream: InputStream, handler: UploadPhotoHandler) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                task.value?.let {
//                    val progress = Amplify.Storage.uploadInputStream(it.id, inputStream)
//                    Log.i(TaskDetailViewModel.TAG, "uploadPhoto: progress $progress")
//                    val result = progress.result()
//                    Log.i(TaskDetailViewModel.TAG, "uploadPhoto: result $result")
//                    inputStream.close()
//                    handler.handleImageUploadSuccess()
//                }
//            } catch (e: Exception) {
//                Log.e(TaskDetailViewModel.TAG, "uploadPhoto: Exception on file upload $e")
//                handler.handleImageUploadError()
//            }
//        }
    }

    interface UploadPhotoHandler {
        fun handleImageUploadSuccess()
        fun handleImageUploadError()
    }
}
