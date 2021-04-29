package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.edhou.taskmaster.R
import com.edhou.taskmaster.models.MockListDao
import com.edhou.taskmaster.models.MockListDaoManager
import com.edhou.taskmaster.models.Status
import com.edhou.taskmaster.models.Task

class AddTask : AppCompatActivity() {
    lateinit private var submitAddTask: Button
    lateinit private var mockListDao: MockListDao

    lateinit private var editTaskName: TextView
    lateinit private var editTaskDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)

        mockListDao = MockListDaoManager.getInstance(resources)

        submitAddTask = findViewById(R.id.addTaskButton);

        submitAddTask?.setOnClickListener { view -> submitTask() }
    }

    private fun submitTask() {
        val newTask = Task(id = null,
                name = editTaskName.getText().toString(),
                description = editTaskDescription.getText().toString(),
                status = Status.NEW)
        mockListDao.insert(newTask)
        finish()
    }
}