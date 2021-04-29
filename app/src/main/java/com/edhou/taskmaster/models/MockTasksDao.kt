package com.edhou.taskmaster.models

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import com.edhou.taskmaster.R

class MockListDao(resources: Resources) {
    private var greatestId : Long = 7;

    private var tasksList: MutableList<Task> =listOf(
                Task(
                        id = 1,
                        name = resources.getString(R.string.task1_name),
                        description = resources.getString(R.string.task1_description),
                        status = Status.NEW
                ),
                Task(
                        id = 2,
                        name = resources.getString(R.string.task2_name),
                        description = resources.getString(R.string.task2_description),
                        status = Status.NEW
                ),
                Task(
                        id = 3,
                        name = resources.getString(R.string.task3_name),
                        description = resources.getString(R.string.task3_description),
                        status = Status.NEW
                ),
                Task(
                        id = 4,
                        name = resources.getString(R.string.task4_name),
                        description = resources.getString(R.string.task4_description),
                        status = Status.NEW
                ),
                Task(
                        id = 5,
                        name = resources.getString(R.string.task4_name),
                        description = resources.getString(R.string.task4_description),
                        status = Status.NEW
                ),
                Task(
                        id = 6,
                        name = resources.getString(R.string.task4_name),
                        description = resources.getString(R.string.task4_description),
                        status = Status.NEW
                ),
                Task(
                        id = 7,
                        name = resources.getString(R.string.task4_name),
                        description = resources.getString(R.string.task4_description),
                        status = Status.NEW
                )
        ).toMutableList()

    public fun getTasksList(): List<Task> {
        return tasksList.toList()
    }

    public fun insert(task: Task) {
        if (task.id != null) {
            if (tasksList.find { it.id == task.id } == null) tasksList.add(task)
        } else {
            task.id = ++greatestId;
            tasksList.add(task)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    public fun remove(id: Long) {
        tasksList.removeIf { it.id == id }
    }

    public fun remove(task: Task) {
        tasksList.remove(task)
    }

    public fun update(task: Task) {
        tasksList.indexOfFirst { it.id == task.id }?.let {
            tasksList.set(it, task)
        }
    }

    public fun findById(id: Long): Task? {
        return tasksList.find { it.id == id }
    }

    public fun findByStatus(status: Status): Task? {
        return tasksList.find { it.status == status }
    }
}

object MockListDaoManager {
    private var mockListDao: MockListDao? = null

    public fun getInstance(resources: Resources) : MockListDao {
        if (mockListDao == null) {
            mockListDao = MockListDao(resources)
        }
        return mockListDao as MockListDao
    }
}