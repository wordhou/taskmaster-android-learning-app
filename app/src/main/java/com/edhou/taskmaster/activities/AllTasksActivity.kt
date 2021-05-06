package com.edhou.taskmaster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edhou.taskmaster.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)
    }
}