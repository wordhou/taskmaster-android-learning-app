package com.edhou.taskmaster.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        val name: String,
        val description: String,
        val status: Status
)