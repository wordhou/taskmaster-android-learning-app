package com.edhou.taskmaster.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.addTask.AddTaskActivity
import com.edhou.taskmaster.taskDetail.TASK_ID
import com.edhou.taskmaster.taskDetail.TaskDetailActivity
import com.edhou.taskmaster.taskList.TasksAdapter
import com.edhou.taskmaster.taskList.TasksListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MAINDEBUG"
    private lateinit var prefs: SharedPreferences
    private lateinit var tasksAdapter: TasksAdapter

    private val viewModel: TasksListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.addTaskLinkButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTaskActivity::class.java))
        }

        findViewById<Button>(R.id.allTasksButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllTasksActivity::class.java))
        }

        findViewById<Button>(R.id.toSettingsButton)?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)

        tasksAdapter = TasksAdapter({ adapterOnClick(it) }, resources)

        val recyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tasksAdapter

        viewModel.tasksList.observe(this, Observer {
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
        prefs.getString(SettingsActivity.USER_NAME, null)?.let {
            findViewById<TextView>(R.id.myTasksHeading)?.setText("$it's Tasks", TextView.BufferType.NORMAL)
        }
        prefs.getStringSet(SettingsActivity.USER_TEAMS, null)?.let {
            val userTeams = it.map { id -> TeamData.justId(id) }.toSet()
            viewModel.setUserTeams(userTeams)
        }

        viewModel.update()
    }
}