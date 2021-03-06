package com.edhou.taskmaster.addTask

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.activities.AddTeam
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.team.TeamsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var submitAddTask: Button
    private lateinit var application: TaskmasterApplication
    private lateinit var editTaskName: TextView
    private lateinit var editTaskDescription: TextView
    private lateinit var teamSelectorSpinner: Spinner
    private lateinit var gotoAddTeamButton: Button
    private lateinit var previewImageView: ImageView
    private lateinit var addTaskUploadButton: Button
    private val tasksListViewModel: TasksListViewModel by viewModels()
    private val teamsListViewModel: TeamsListViewModel by viewModels()
    private val addTaskViewModel: AddTaskViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Log.i(TAG, "activityResult uri: $it")
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                addTaskViewModel.saveImage(inputStream, applicationContext.filesDir)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        application = getApplication() as TaskmasterApplication
        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)
        teamSelectorSpinner = findViewById(R.id.teamSelectSpinner)
        gotoAddTeamButton = findViewById(R.id.addTaskToAddTeamButton)
        addTaskUploadButton = findViewById(R.id.addTaskUploadButton)
        previewImageView = findViewById(R.id.addTaskImageView)
        submitAddTask = findViewById(R.id.addTaskButton)

        submitAddTask.setOnClickListener { submitTask() }

        val viewTeamsList = mutableListOf<TeamData>()

        val adapter = ArrayAdapter(this,
                R.layout.simple_list_item_team,
                R.id.simpleListItemText,
                viewTeamsList
        )

        teamSelectorSpinner.adapter = adapter
        teamSelectorSpinner.onItemSelectedListener = this

        teamsListViewModel.teams.observe(this, {
            Log.i(TAG, "observed change in teams list on ${Thread.currentThread().name} thread")
            adapter.clear()
            adapter.addAll(it)
        })

        gotoAddTeamButton.setOnClickListener {
            startActivity(Intent(this@AddTaskActivity, AddTeam::class.java))
        }

        addTaskUploadButton.setOnClickListener {
            getContent.launch("image/*")
        }

        addTaskViewModel.finishedAddTask.observe(this, Observer {
            if (it) finish()
        })

        addTaskViewModel.image.observe(this, Observer {
            previewImageView.setImageBitmap(it)
        })

        when (intent.type) {
            "text/plain" -> handleIntentPlainText(intent.getStringExtra(Intent.EXTRA_TEXT));
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/bmp" -> handleIntentImage(intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM))
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun handleIntentImage(stream: Uri?) {
        stream?.let {
            Log.i(TAG, "activityResult uri: $it")
            val inputStream = contentResolver.openInputStream(it)
            if (inputStream != null) {
                Log.i(TAG, "handleIntentImage: calling viewModel.saveImage()")
                addTaskViewModel.saveImage(inputStream, applicationContext.filesDir)
            }
        }
    }

    private fun handleIntentPlainText(text: String?) {
        text?.let { editTaskName.text = it }
    }

    private fun submitTask() {
        if (editTaskName.text.isBlank()) {
            Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show()
            return
        }
        val currentTeam = addTaskViewModel.currentlySelectedTeam.value
        if (currentTeam == null) {
            Toast.makeText(this, "A team needs to be selected", Toast.LENGTH_SHORT).show()
            return
        }
        addTaskViewModel.addTask(editTaskName.text.toString(), editTaskDescription.text.toString())
    }

    override fun onResume() {
        super.onResume()
        teamsListViewModel.update()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)?.let {
            Log.i(TAG, "onItemSelected: $it on thread ${Thread.currentThread().name}")
            addTaskViewModel.selectTeam(it as TeamData)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i(TAG, "Items unselected on thread ${Thread.currentThread().name}!")
        addTaskViewModel.unselectTeam()
    }

    companion object {
        private val TAG: String = "AddTaskActivity"
    }
}