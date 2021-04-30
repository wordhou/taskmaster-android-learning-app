package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.db.AppDatabase
import com.edhou.taskmaster.db.TasksDao
import com.edhou.taskmaster.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {
    lateinit private var submitAddTask: Button
    lateinit private var appDb: AppDatabase
    lateinit private var tasksDao: TasksDao

    lateinit private var application: TaskmasterApplication
    lateinit private var editTaskName: TextView
    lateinit private var editTaskDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)

        application = getApplication() as TaskmasterApplication

        submitAddTask = findViewById(R.id.addTaskButton)
        submitAddTask.setOnClickListener { _ -> submitTask() }
    }

    private fun submitTask() {
        val newTask = Task(id = null,
                name = editTaskName.getText().toString(),
                description = editTaskDescription.getText().toString(),
                status = Status.NEW)
        lifecycleScope.launch(Dispatchers.IO) {
            application.repository.insert(newTask)
        }
        finish()
    }
}