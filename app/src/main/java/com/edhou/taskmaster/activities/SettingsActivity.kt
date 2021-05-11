package com.edhou.taskmaster.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import com.edhou.taskmaster.R
import com.edhou.taskmaster.auth.AuthViewModel
import com.edhou.taskmaster.auth.SignInActivity
import com.edhou.taskmaster.auth.SignUpActivity
import com.edhou.taskmaster.taskList.TasksListViewModel
import com.edhou.taskmaster.team.TeamsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity(), AdapterView.OnItemClickListener, AuthViewModel.SignOutHandler {
    private lateinit var prefs: SharedPreferences
    private lateinit var editPrefs: SharedPreferences.Editor
    private lateinit var multiSelectTeamListView: ListView
    private lateinit var adapter: ArrayAdapter<TeamData>
    private val teamsListViewModel: TeamsListViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setupMultiSelectListView()

        prefs = getSharedPreferences(getString(R.string.user_details_shared_preferences), MODE_PRIVATE)
        editPrefs = prefs.edit()

        findViewById<Button>(R.id.settingsSave)?.setOnClickListener { saveSettings() }

        prefs.getString(USER_NAME, null)?.let {
            findViewById<EditText>(R.id.editUserName)?.setText(it, TextView.BufferType.EDITABLE)
        }

        findViewById<Button>(R.id.buttonLinkSignUp)?.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, SignUpActivity::class.java))
        }

        findViewById<Button>(R.id.buttonLinkLogIn)?.setOnClickListener {
            Log.i(TAG, "logInButton, currentUser: ${Amplify.Auth.getCurrentUser()}")
            if (Amplify.Auth.getCurrentUser() != null) {
                authViewModel.signOut(this)
            } else {
                startActivity(Intent(this@SettingsActivity, SignInActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: OnResume settings")
        findViewById<Button>(R.id.buttonLinkLogIn).text = resources.getString(R.string.sign_in)
        findViewById<TextView>(R.id.loggedInStatus).text = ""
        Amplify.Auth.getCurrentUser()?.let {
            Log.i(TAG, "onCreate: YOU ARE NOW LOGGED IN WITH ID ${it.userId}")
            Log.i(TAG, "onCreate: YOU ARE NOW LOGGED IN WITH USERNAME ${it.username}")
            findViewById<Button>(R.id.buttonLinkLogIn).text = resources.getString(R.string.log_out)
            findViewById<TextView>(R.id.loggedInStatus).text = "You are now logged in as ${it.username}"
        }
    }

    fun setupMultiSelectListView() {
        multiSelectTeamListView = findViewById(R.id.multiSelectTeamListView)

        adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                mutableListOf<TeamData>()
        )

        teamsListViewModel.teams.observe(this, {
            Log.i(TAG, "observed change in teams list on ${Thread.currentThread().name} thread")
            adapter.clear()
            adapter.addAll(it)
            updateSelected()
        })

        multiSelectTeamListView.adapter = adapter
        multiSelectTeamListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        multiSelectTeamListView.onItemClickListener = this
        multiSelectTeamListView.itemsCanFocus = false

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val checked: SparseBooleanArray = multiSelectTeamListView.checkedItemPositions
        val team = parent?.getItemAtPosition(position)

        Log.i(TAG, "onClick: ${getSelectedStringSet()}")
        //editPrefs.putStringSet
    }


    private fun saveSettings() {
        findViewById<EditText>(R.id.editUserName)?.text.toString().let {
            Toast.makeText(this@SettingsActivity, "Saving...", Toast.LENGTH_SHORT).show()
            editPrefs.putString(USER_NAME, it)
            editPrefs.putStringSet(USER_TEAMS, getSelectedStringSet())
            Log.i(TAG, "saveSettings: ${getSelectedStringSet()}")
            editPrefs.apply()
        }
    }

    private fun getSelectedStringSet(): Set<String> {
        val checked: SparseBooleanArray = multiSelectTeamListView.checkedItemPositions
        val result: MutableSet<String> = mutableSetOf<String>()

        for (i in 0 until adapter.count)
            if (checked.get(i)) adapter.getItem(i)?.id?.let { result.add(it) }

        return result
    }

    private fun updateSelected() {
        val ids = prefs.getStringSet(USER_TEAMS, mutableSetOf<String>())
        //Log.i(TAG, "updateSelected: $ids")
        if (ids == null) return
        for (i in 0 until adapter.count)
            if (ids.contains(adapter.getItem(i)?.id)) {
                multiSelectTeamListView.setItemChecked(i, true)
            } else multiSelectTeamListView.setItemChecked(i, false)
    }

    companion object {
        const val USER_NAME = "user_name"
        const val USER_TEAMS = "user_teams"
        const val TAG = "SettingsActivity"
    }

    override fun handleError(error: AuthException) {
        //
    }

    override fun handleSuccess() {
        Log.i(TAG, "handleSuccess: Logged out")
        onResume()
    }
}