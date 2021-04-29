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
import com.edhou.taskmaster.models.MockListDao
import com.edhou.taskmaster.models.MockListDaoManager
import com.edhou.taskmaster.models.Task
import com.edhou.taskmaster.taskList.TasksAdapter

class MainActivity : AppCompatActivity() {
    private val TAG = "MAINDEBUG"
    lateinit var prefs: SharedPreferences
    lateinit var tasksAdapter: TasksAdapter

    lateinit private var mockListDao : MockListDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mockListDao = MockListDaoManager.getInstance(resources)


        findViewById<Button>(R.id.addTaskLinkButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTask::class.java)) }

        findViewById<Button>(R.id.allTasksButton)?.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllTasks::class.java)) }

        findViewById<Button>(R.id.toSettingsButton)?.setOnClickListener{
            startActivity(Intent(this, Settings::class.java)) }

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)


        tasksAdapter = TasksAdapter ({ task -> adapterOnClick(task) }, resources )
        tasksAdapter.submitList(mockListDao.getTasksList())

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

        tasksAdapter.submitList(mockListDao.getTasksList())
        prefs.getString("name", null)?.let {
            findViewById<TextView>(R.id.myTasksHeading)?.setText("$it's Tasks", TextView.BufferType.NORMAL)
        }
    }
}