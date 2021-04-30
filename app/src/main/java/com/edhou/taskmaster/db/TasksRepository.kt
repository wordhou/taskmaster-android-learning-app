package com.edhou.taskmaster.db

import androidx.annotation.WorkerThread
import com.edhou.taskmaster.models.Task

class TasksRepository(private val tasksDao: TasksDao) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getTasksList(): List<Task> {
        return tasksDao.getTasksList()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun findById(id: Long): Task {
        return tasksDao.findById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        tasksDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(task: Task) {
        tasksDao.delete(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(task: Task) {
        tasksDao.update(task)
    }
}