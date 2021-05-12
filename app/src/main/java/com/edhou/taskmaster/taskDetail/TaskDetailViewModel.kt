package com.edhou.taskmaster.taskDetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.*
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.kotlin.core.Amplify
import com.amplifyframework.kotlin.storage.Storage
import com.amplifyframework.storage.result.StorageDownloadFileResult
import com.edhou.taskmaster.db.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(val tasksRepository: TasksRepository) : ViewModel() {
    private var taskId: String = ""
    val _task: MutableLiveData<TaskData> = MutableLiveData()
    val task: LiveData<TaskData>
        get() = _task

    val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap>
        get() = _image

    fun setTaskId(id: String) {
        if (id != taskId) {
            tasksRepository.findByIdFlow(id).onEach { _task.value = it }.launchIn(viewModelScope)
            taskId = id
        }
    }

    fun update(taskData: TaskData) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksRepository.update(taskData)
        }
    }

    fun uploadPhoto(inputStream: InputStream, handler: UploadPhotoHandler) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                task.value?.let {
                    val progress = Amplify.Storage.uploadInputStream(it.id, inputStream)
                    Log.i(TAG, "uploadPhoto: progress $progress")
                    val result = progress.result()
                    Log.i(TAG, "uploadPhoto: result $result")
                    inputStream.close()
                    handler.handleImageUploadSuccess()
                }
            } catch (e: Exception) {
                Log.e(TAG, "uploadPhoto: Exception on file upload $e")
                handler.handleImageUploadError()
            }
        }
    }

    interface UploadPhotoHandler {
        fun handleImageUploadSuccess()
        fun handleImageUploadError()
    }

    fun downloadPhoto(dir: File) {
        viewModelScope.launch {
            try {
                task.value?.let {
                    val file = File(dir, it.id)
                    val result = Amplify.Storage.downloadFile(it.id, file)
                    Log.i(TAG, "downloadPhoto: file path ${result.result().file.path}")
                    _image.value = BitmapFactory.decodeFile(result.result().file.path)
                    Log.i(TAG, "downloadPhoto: image updated")
                }
            } catch (e: Exception) {
                Log.e(TAG, "uploadPhoto: Exception on file download $e")
            }
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TaskDetailViewModel", "delete: ${Thread.currentThread().name}")
            tasksRepository.delete(task.value!!)
        }
    }

    companion object {
        const val TAG = "TaskDetailViewModel"
    }
}
