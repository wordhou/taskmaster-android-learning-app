package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.edhou.taskmaster.R
import com.edhou.taskmaster.models.tasksList
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.taskList.TasksListViewModelFactory

const val TASK_ID = "TASK_ID"
class TaskDetailActivity : AppCompatActivity() {
    private val tasksListViewModel by viewModels<TasksListViewModel> { TasksListViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        var currentTaskId: Long? = null

        /* Connect variables to UI elements. */
        val taskName: TextView = findViewById(R.id.taskName)
        val taskDescription: TextView = findViewById(R.id.taskDescription)

        val bundle: Bundle? = intent.extras
        bundle?.apply { currentTaskId = getLong(TASK_ID) }
        val currentTask = currentTaskId?.let {
            // TODO: Hookup to ViewModel
            tasksList(resources).find({ task -> task.id == it })
        }

        currentTask?.apply {
            taskName.text = name
            taskDescription.text = description
        }
    }
}