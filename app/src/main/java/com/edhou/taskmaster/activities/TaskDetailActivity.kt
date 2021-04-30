package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TASK_ID = "TASK_ID"

class TaskDetailActivity : AppCompatActivity() {
    // private val tasksListViewModel by viewModels<TasksListViewModel> { TasksListViewModelFactory(this) }
    private lateinit var application: TaskmasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        application = getApplication() as TaskmasterApplication

        var currentTaskId: Long? = null
        var currentTask: Task? = null

        /* Connect variables to UI elements. */
        val taskName: TextView = findViewById(R.id.taskName)
        val taskDescription: TextView = findViewById(R.id.taskDescription)
        val taskStatus: TextView = findViewById(R.id.taskStatus)

        val bundle: Bundle? = intent.extras
        bundle?.apply { currentTaskId = getLong(TASK_ID) }

        lifecycleScope.launch(Dispatchers.IO) {
            if (currentTaskId != null) {
                currentTask = application.repository.findById(currentTaskId!!)
                currentTask?.apply {
                    taskName.text = name
                    taskDescription.text = description
                    taskStatus.text = status.getString(resources)
                }
            }
        }

        findViewById<Button>(R.id.deleteTaskButton)?.setOnClickListener {
            kotlin.run {
                if (currentTask != null) {
                    lifecycleScope.launch {
                        application.repository.delete(currentTask!!)
                        finish()
                    }
                }
            }
        }
    }
}