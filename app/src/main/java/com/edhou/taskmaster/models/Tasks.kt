package com.edhou.taskmaster.models

import android.content.res.Resources
import com.edhou.taskmaster.R

fun tasksList(resources: Resources): List<Task> {
    return listOf(
            Task(
                    id = 1,
                    name = resources.getString(R.string.task1_name),
                    description = resources.getString(R.string.task1_description)
            ),
            Task(
                    id = 2,
                    name = resources.getString(R.string.task2_name),
                    description = resources.getString(R.string.task2_description)
            ),
            Task(
                    id = 3,
                    name = resources.getString(R.string.task3_name),
                    description = resources.getString(R.string.task3_description)
            ),
            Task(
                    id = 4,
                    name = resources.getString(R.string.task4_name),
                    description = resources.getString(R.string.task4_description)
            )
    )
}