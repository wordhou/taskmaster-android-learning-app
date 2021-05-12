package com.edhou.taskmaster.taskDetail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.activities.AddTeam
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.utils.StatusDisplayer
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

const val TASK_ID = "TASK_ID"

@AndroidEntryPoint
class TaskDetailActivity : AppCompatActivity(), TaskDetailViewModel.UploadPhotoHandler {
    private lateinit var application: TaskmasterApplication

    private val viewModel: TaskDetailViewModel by viewModels()
    private val tasksListViewModel: TasksListViewModel by viewModels()

    var currentTaskId: String? = null

//    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
//        if (it) Log.i(TAG, "Picture successfully taken")
//        else Log.i(TAG, "Picture successfully taken")
//    }

    private val openDocument = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) viewModel.uploadPhoto(inputStream, this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        application = getApplication() as TaskmasterApplication

        /* Connect variables to UI elements. */
        val taskName: TextView = findViewById(R.id.taskName)
        val taskDescription: TextView = findViewById(R.id.taskDescription)
        val taskStatus: TextView = findViewById(R.id.taskStatus)
        val uploadPictureButton: Button = findViewById(R.id.uploadPictureButton)
        val taskImage: ImageView = findViewById(R.id.imageView)

        val bundle: Bundle? = intent.extras
        bundle?.apply { currentTaskId = getString(TASK_ID) }

        currentTaskId?.let {
            viewModel.setTaskId(it)
        }

        viewModel.task.observe(this, Observer {
            it?.apply {
                taskName.text = name
                taskDescription.text = description
                taskStatus.text = StatusDisplayer.statusToString(status, resources)
                viewModel.downloadPhoto(applicationContext.filesDir)
            }
        })

        viewModel.image.observe(this, Observer {
            taskImage.setImageBitmap(it)
        })

        findViewById<Button>(R.id.deleteTaskButton)?.setOnClickListener {
            kotlin.run {
                tasksListViewModel.delete(currentTaskId)
                viewModel.delete()
                finish()
            }
        }

        findViewById<Button>(R.id.linkAddTeamButton)?.setOnClickListener {
            startActivity(Intent(this@TaskDetailActivity, AddTeam::class.java))
        }

        uploadPictureButton.setOnClickListener {
            openDocument.launch(arrayOf("image/*"))
        }
    }

    fun uploadImage() {
//        val file = File(filesDir, "picFromCamera")
//        val uri: Uri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", file)
    }

    companion object {
        private const val TAG = "TaskDetail"
    }

    override fun handleImageUploadSuccess() {
        viewModel.downloadPhoto(applicationContext.filesDir)
    }

    override fun handleImageUploadError() {
        TODO("Not yet implemented")
    }
}