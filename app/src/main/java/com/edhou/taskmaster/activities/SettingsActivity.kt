package com.edhou.taskmaster.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.amplifyframework.datastore.generated.model.TeamData
import com.edhou.taskmaster.R
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.taskList.TasksListViewModelFactory
import com.edhou.taskmaster.team.TeamsListViewModel
import com.edhou.taskmaster.team.TeamsListViewModelFactory

class SettingsActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var prefs: SharedPreferences
    private lateinit var editPrefs: SharedPreferences.Editor
    private lateinit var multiSelectTeamListView: ListView
    private lateinit var adapter: ArrayAdapter<TeamData>
    private val teamsListViewModel: TeamsListViewModel by viewModels { TeamsListViewModelFactory(this) }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        multiSelectTeamListView = findViewById(R.id.multiSelectTeamListView)

        adapter = ArrayAdapter(
                this,
                R.layout.simple_list_item_multiple_choice,
                R.id.multChoiceTeamNameText,
                mutableListOf<TeamData>()
        )

        teamsListViewModel.teams.observe(this, {
            Log.i(TAG, "observed change in teams list on ${Thread.currentThread().name} thread")
            adapter.clear()
            adapter.addAll(it)
            adapter.notifyDataSetChanged()
        })

        multiSelectTeamListView.adapter = adapter
        multiSelectTeamListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        multiSelectTeamListView.onItemClickListener = this
        multiSelectTeamListView.itemsCanFocus = false

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)
        editPrefs = prefs.edit()

        findViewById<Button>(R.id.settingsSave)?.setOnClickListener { saveSettings() }

        prefs.getString("name", null)?.let {
            Log.i("DEBUGSETTINGS", "onCreate: String with key name is $it")
            findViewById<EditText>(R.id.editUserName)?.setText(it, TextView.BufferType.EDITABLE)
        }
    }

    private fun saveSettings() {
        findViewById<EditText>(R.id.editUserName)?.text.toString().let {
            Toast.makeText(this@SettingsActivity, "Saving...", Toast.LENGTH_SHORT).show()
            editPrefs.putString("name", it)
            editPrefs.apply()
        }
    }

    companion object {
        const val TAG = "SettingsActivity"
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       // val checked: SparseBooleanArray = multiSelectTeamListView.checkedItemPositions
        Log.i(TAG, "onItemClick: position: $position")
        multiSelectTeamListView.setItemChecked(position, true)
        val team = parent?.getItemAtPosition(position)

        Log.i(TAG, "onClick: $team")
    }
}