package com.edhou.taskmaster.addTask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import com.amplifyframework.kotlin.storage.Storage
import com.amplifyframework.storage.result.StorageUploadFileResult
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.taskDetail.TaskDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
        val tasksRepository: TasksRepository,
        //val teamsRepository: TeamsRepository
        ) : ViewModel() {
    private val _currentlySelectedTeam: MutableLiveData<TeamData> = MutableLiveData()
    private val _finishedAddTask: MutableLiveData<Boolean> = MutableLiveData(false)
    val finishedAddTask: LiveData<Boolean>
        get() = _finishedAddTask

    val currentlySelectedTeam: LiveData<TeamData>
        get() = _currentlySelectedTeam

    val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap>
        get() = _image

    private var tempFile: File? = null;

    fun selectTeam(teamData: TeamData) { _currentlySelectedTeam.value = teamData }

    fun unselectTeam() { _currentlySelectedTeam.value = null }

    fun addTask(name: String, description: String?) {
        val newTask: TaskData = TaskData.builder()
                .name(name)
                .description(description)
                .status(Status.NEW)
                .team(currentlySelectedTeam.value)
                .hasPicture(tempFile != null)
                .build()

        viewModelScope.launch {
            try {
                val progress = tempFile?.let { Amplify.Storage.uploadFile(newTask.id, it) }
                val response = async { Amplify.API.mutate(ModelMutation.create(newTask)) }
                val uploadFileResult = progress?.result()
                Log.i(TaskDetailViewModel.TAG, "addTask uploadPhoto: $uploadFileResult")
                Log.i(TAG, "addTask API call: ${response.await()}")
                _finishedAddTask.postValue(true)
            } catch (e: Exception) {
                Log.e(TAG, "addTask: $e")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveImage(inputStream: InputStream, filesDir: File?) {
        viewModelScope.launch {
            tempFile = File(filesDir, "tempFile")
            val outputStream = FileOutputStream(tempFile)
            try {
                FileUtils.copy(inputStream, outputStream)
                Log.i(TAG, "saveImage: Temp imaged saved to filesystem at ${tempFile!!.path}")
            } finally {
                inputStream.close()
                outputStream.close()
            }
        }
        updateBitmap(tempFile)
    }

    private fun updateBitmap(file: File?) {
        file?.let {
            _image.value = BitmapFactory.decodeFile(it.path)
        }
    }

    interface UploadPhotoHandler {
        fun handleImageUploadSuccess()
        fun handleImageUploadError()
    }

    companion object {
        const val TAG = "AddTaskViewModel"
    }
}
