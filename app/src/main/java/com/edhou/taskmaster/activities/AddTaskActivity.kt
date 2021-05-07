package com.edhou.taskmaster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.addTask.AddTaskViewModel
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.team.TeamsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var submitAddTask: Button
    private lateinit var application: TaskmasterApplication
    private lateinit var editTaskName: TextView
    private lateinit var editTaskDescription: TextView
    private lateinit var teamSelectorSpinner: Spinner
    private lateinit var gotoAddTeamButton: Button
//    private val tasksListViewModel: TasksListViewModel by viewModels { TasksListViewModelFactory(this) }
//    private val teamsListViewModel: TeamsListViewModel by viewModels { TeamsListViewModelFactory(this) }
//    private val addTaskViewModel: AddTaskViewModel by viewModels { AddTaskViewModelFactory(this) }
    private val tasksListViewModel: TasksListViewModel by viewModels()
    private val teamsListViewModel: TeamsListViewModel by viewModels()
    private val addTaskViewModel: AddTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        application = getApplication() as TaskmasterApplication
        editTaskName = findViewById(R.id.editTaskName)
        editTaskDescription = findViewById(R.id.editTaskDescription)
        teamSelectorSpinner = findViewById(R.id.teamSelectSpinner)
        gotoAddTeamButton = findViewById(R.id.addTaskToAddTeamButton)
        submitAddTask = findViewById(R.id.addTaskButton)

        submitAddTask.setOnClickListener { submitTask() }

        val viewTeamsList = mutableListOf<TeamData>()

        val adapter = ArrayAdapter(this,
                R.layout.simple_list_item_team,
                R.id.simpleListItemText,
                viewTeamsList
                )

        teamSelectorSpinner.adapter = adapter
        teamSelectorSpinner.onItemSelectedListener = this

        teamsListViewModel.teams.observe(this, {
            Log.i(TAG, "observed change in teams list on ${Thread.currentThread().name} thread")
            adapter.clear()
            adapter.addAll(it)
            adapter.notifyDataSetChanged()
        })

        gotoAddTeamButton?.setOnClickListener {
            startActivity(Intent(this@AddTaskActivity, AddTeam::class.java))
        }
    }

    private fun submitTask() {
        if (editTaskName.text.isBlank()) {
            Toast.makeText(this, "Name cannot be blank", Toast.LENGTH_SHORT).show()
            return;
        }
        val currentTeam = addTaskViewModel.currentlySelectedTeam.value
        if (currentTeam == null) {
            Toast.makeText(this, "A team needs to be selected", Toast.LENGTH_SHORT).show()
            return;
        }
        val newTask = TaskData.builder()
                .name(editTaskName.text.toString())
                .description(editTaskDescription.text.toString())
                .team(currentTeam)
                .status(Status.NEW)
                .build()
        lifecycleScope.launch(Dispatchers.IO) {
            //tasksRepository.insert(newTask)
            addTaskViewModel.addTask(newTask)
            //viewModel should do this
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        teamsListViewModel.update()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)?.let {
            Log.i(TAG, "onItemSelected: $it on thread ${Thread.currentThread().name}")
            addTaskViewModel.selectTeam(it as TeamData)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.i(TAG, "Items unselected on thread ${Thread.currentThread().name}!")
        addTaskViewModel.unselectTeam()
    }

    companion object {
        private final val TAG : String = "AddTaskActivity"
    }

}