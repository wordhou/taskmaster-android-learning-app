package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {
    private lateinit var submitAddTask: Button
    private lateinit var application: TaskmasterApplication
    private lateinit var editTaskName: TextView
    private lateinit var editTaskDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)

        application = getApplication() as TaskmasterApplication

        submitAddTask = findViewById(R.id.addTaskButton)
        submitAddTask.setOnClickListener { submitTask() }
    }

    private fun submitTask() {
        val newTask = TaskData.builder()
                .name(editTaskName.text.toString())
                .description(editTaskDescription.text.toString())
                .status(Status.NEW)
                .build()
        lifecycleScope.launch(Dispatchers.IO) {
            application.tasksRepository.insert(newTask)
        }
        finish()
    }
}