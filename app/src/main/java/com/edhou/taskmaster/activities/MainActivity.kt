package com.edhou.taskmaster.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edhou.taskmaster.R
import com.edhou.taskmaster.models.Task
import com.edhou.taskmaster.models.tasksList
import com.edhou.taskmaster.taskList.TasksAdapter
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.taskList.TasksListViewModelFactory

class MainActivity : AppCompatActivity() {
    val TAG = "MAINDEBUG"
    lateinit var prefs: SharedPreferences
    private val tasksListViewModel by viewModels<TasksListViewModel> { TasksListViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.addTaskButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTask::class.java)) }

        findViewById<Button>(R.id.allTasksButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllTasks::class.java)) }

        findViewById<Button>(R.id.toSettingsButton)?.setOnClickListener{
            startActivity(Intent(this, Settings::class.java)) }

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)


        val tasksAdapter = TasksAdapter { task -> adapterOnClick(task) }
        tasksAdapter.submitList(tasksList(resources))
        Log.i(TAG, "itemCount: ${tasksAdapter.itemCount}")
        Log.i(TAG, "currentList: ${tasksAdapter.currentList}")
        val recyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tasksAdapter;
    }

    private fun adapterOnClick(task: Task) {
        val intent = Intent(this, TaskDetailActivity()::class.java)
        intent.putExtra(TASK_ID, task.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        prefs.getString("name", null)?.let {
            findViewById<TextView>(R.id.myTasksHeading)?.setText("$it's Tasks", TextView.BufferType.NORMAL)
        }
    }
}