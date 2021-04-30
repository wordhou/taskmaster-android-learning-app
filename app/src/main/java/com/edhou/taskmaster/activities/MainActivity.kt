package com.edhou.taskmaster.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.models.Task
import com.edhou.taskmaster.taskList.TasksAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MAINDEBUG"
    private lateinit var prefs: SharedPreferences
    private lateinit var tasksAdapter: TasksAdapter

    lateinit private var application: TaskmasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        application = getApplication() as TaskmasterApplication

        findViewById<Button>(R.id.addTaskLinkButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTaskActivity::class.java)) }

        findViewById<Button>(R.id.allTasksButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllTasksActivity::class.java)) }

        findViewById<Button>(R.id.toSettingsButton)?.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java)) }

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)

        tasksAdapter = TasksAdapter ({ task -> adapterOnClick(task) }, resources )

        val recyclerView: RecyclerView = findViewById(R.id.tasksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = tasksAdapter;

        lifecycleScope.launch(Dispatchers.IO) {
            tasksAdapter.submitList(application.repository.getTasksList())
        }
    }

    private fun adapterOnClick(task: Task) {
        val intent = Intent(this, TaskDetailActivity()::class.java)
        intent.putExtra(TASK_ID, task.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch (Dispatchers.IO) {
            tasksAdapter.submitList(application.repository.getTasksList())
        }
        prefs.getString("name", null)?.let {
            findViewById<TextView>(R.id.myTasksHeading)?.setText("$it's Tasks", TextView.BufferType.NORMAL)
        }
    }
}