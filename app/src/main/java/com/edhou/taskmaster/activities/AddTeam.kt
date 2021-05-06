package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.TaskmasterApplication
import com.edhou.taskmaster.team.TeamsListViewModel
import com.edhou.taskmaster.team.TeamsListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTeam : AppCompatActivity() {
    private lateinit var submitAddTeam: Button
    private lateinit var application: TaskmasterApplication
    private lateinit var editTeamName: TextView
    private val teamsListViewModel: TeamsListViewModel by viewModels { TeamsListViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)

        editTeamName = findViewById(R.id.editTextTeamName)
        application = getApplication() as TaskmasterApplication

        submitAddTeam = findViewById(R.id.submitAddTeam)
        submitAddTeam.setOnClickListener { submitTeam() }
    }

    private fun submitTeam() {
        val newTeam = TeamData.builder()
                .name(editTeamName.text.toString())
                .build()
        teamsListViewModel.insert(newTeam)
//        lifecycleScope.launch(Dispatchers.IO) {
//            application.teamsRepository.insert(newTeam)
//        }
        finish()
    }
}