package com.edhou.taskmaster.db

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import com.edhou.taskmaster.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.security.auth.login.LoginException

class TasksRepository(private val tasksDao: TasksDao) {
    companion object {
        const val TAG = "TasksRepo"
    }

    fun getTasksListFlow(): Flow<List<TaskData>> = flow {
        val items = Amplify.API
                .query(ModelQuery.list(TaskData::class.java)).data.items
        emit(items.toList())
        //emit(items.map{ taskData -> Task.fromAmplify(taskData) })
        //tasksDao.getTasksList()
    }

    suspend fun getTasksList(): List<TaskData> {
        val items = Amplify.API
                .query(ModelQuery.list(TaskData::class.java)).data.items
        return items.toList()
                //.map{ taskData -> Task.fromAmplify(taskData) })
        //tasksDao.getTasksList()
    }

    fun findByIdFlow(id: String): Flow<TaskData> = flow {
        val taskData = Amplify.API.query(ModelQuery.get(TaskData::class.java, id)).data
        emit(taskData)
        //emit(Task.fromAmplify(taskData))
        //tasksDao.findById(id).distinctUntilChanged()
    }

    suspend fun findById(id: String): TaskData {
        return Amplify.API.query(ModelQuery.get(TaskData::class.java, id)).data
        //return Task.fromAmplify(Amplify.API.query(ModelQuery.get(TaskData::class.java, id)).data)
        //tasksDao.findById(id).distinctUntilChanged()
    }

    suspend fun insert(taskData: TaskData) {
        Log.i(TAG, "inserting $taskData")
        try {
            val response = Amplify.API.mutate(ModelMutation.create(taskData)).data
            Log.i(TAG, "insert successful: $taskData")
        } catch(error: ApiException) {
            Log.e(TAG, "insert: " + taskData.toString(), error)
        }
        //tasksDao.insert(Task.fromAmplify(taskData))
    }
//
//    suspend fun insert(task: Task) {
//        insert(task.amplify())
//    }
//
//    suspend fun delete(task: Task) {
//        delete(task.amplify())
//    }

    suspend fun delete(taskData: TaskData) {
        //tasksDao.delete(Task.fromAmplify(taskData))
        Log.i(TAG, "deleting $taskData")
        try {
            val response = Amplify.API.mutate(ModelMutation.delete(taskData)).data
            Log.i(TAG, "delete successful: $taskData")
        } catch(error: ApiException) {
            Log.e(TAG, "delete: " + taskData.toString(), error)
        }
    }

    suspend fun update(task: Task) {
        //tasksDao.update(task)
    }
}