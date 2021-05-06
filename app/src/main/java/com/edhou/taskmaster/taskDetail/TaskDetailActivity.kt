package com.edhou.taskmaster.taskDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.Status
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.activities.AddTaskActivity
import com.edhou.taskmaster.activities.AddTeam
import com.edhou.taskmaster.models.Task
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.utils.StatusDisplayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TASK_ID = "TASK_ID"

@AndroidEntryPoint
class TaskDetailActivity : AppCompatActivity() {
    // private val tasksListViewModel by viewModels<TasksListViewModel> { TasksListViewModelFactory(this) }
    private lateinit var application: TaskmasterApplication

    private val viewModel: TaskDetailViewModel by viewModels()
    private val tasksListViewModel: TasksListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        application = getApplication() as TaskmasterApplication

        var currentTaskId: String? = null

        /* Connect variables to UI elements. */
        val taskName: TextView = findViewById(R.id.taskName)
        val taskDescription: TextView = findViewById(R.id.taskDescription)
        val taskStatus: TextView = findViewById(R.id.taskStatus)

        val bundle: Bundle? = intent.extras
        bundle?.apply { currentTaskId = getString(TASK_ID) }

        currentTaskId?.let {
            viewModel.setTaskId(it)
        }

        viewModel.task.observe(this) {
            it?.apply {
                taskName.text = name
                taskDescription.text = description
                taskStatus.text = StatusDisplayer.statusToString(status, resources)
            }
        }

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
    }
}