package com.edhou.taskmaster.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.taskDetail.TASK_ID
import com.edhou.taskmaster.taskDetail.TaskDetailActivity
import com.edhou.taskmaster.taskList.TasksAdapter
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.taskList.TasksListViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MAINDEBUG"
    private lateinit var prefs: SharedPreferences
    private lateinit var tasksAdapter: TasksAdapter

    private val viewModel: TasksListViewModel by viewModels{ TasksListViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.addTaskLinkButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTaskActivity::class.java)) }

        findViewById<Button>(R.id.allTasksButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllTasksActivity::class.java)) }

        findViewById<Button>(R.id.toSettingsButton)?.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java)) }

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)

        tasksAdapter = TasksAdapter ({ adapterOnClick(it) }, resources )

        val recyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tasksAdapter

        viewModel.tasksList.observe(this,  {
            tasksAdapter.submitList(it)
        })
    }

    private fun adapterOnClick(task: TaskData) {
        val intent = Intent(this, TaskDetailActivity()::class.java)
        intent.putExtra(TASK_ID, task.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        prefs.getString("name", null)?.let {
            findViewById<TextView>(R.id.myTasksHeading)?.setText("$it's Tasks", TextView.BufferType.NORMAL)
        }

        viewModel.update()
    }
}