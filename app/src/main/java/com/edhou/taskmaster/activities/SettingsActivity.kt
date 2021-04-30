package com.edhou.taskmaster.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.edhou.taskmaster.R

class SettingsActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private lateinit var editPrefs: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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
}