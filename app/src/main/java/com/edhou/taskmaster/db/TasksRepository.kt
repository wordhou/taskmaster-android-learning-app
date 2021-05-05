package com.edhou.taskmaster.db

import com.amplifyframework.datastore.generated.model.TaskData
import com.edhou.taskmaster.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class TasksRepository(private val tasksDao: TasksDao) {
    fun getTasksList(): Flow<List<Task>> {
        return tasksDao.getTasksList()
    };

    fun findById(id: String): Flow<Task> {
        return tasksDao.findById(id).distinctUntilChanged()
    }

    suspend fun insert(taskData: TaskData) {
        tasksDao.insert(Task.fromAmplify(taskData))
    }

    suspend fun insert(task: Task) {
        tasksDao.insert(task)
    }

    suspend fun delete(task: Task) {
        tasksDao.delete(task)
    }

    suspend fun delete(taskData: TaskData) {
        tasksDao.delete(Task.fromAmplify(taskData))
    }

    suspend fun update(task: Task) {
        tasksDao.update(task)
    }
}