package com.edhou.taskmaster.models

data class Task(
        var id: Long?,
        val name: String,
        val description: String,
        val status: Status
)