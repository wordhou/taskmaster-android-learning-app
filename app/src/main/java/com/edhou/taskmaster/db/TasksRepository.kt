package com.edhou.taskmaster.db

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.TaskData
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepository @Inject constructor(private val tasksDao: TasksDao) {
    companion object {
        const val TAG = "TasksRepo"
    }

    fun getTasksListFlow(): Flow<List<TaskData>> = flow {
        val items = Amplify.API
                .query(ModelQuery.list(TaskData::class.java)).data.items
        emit(items.toList())
    }

    fun getTasksListByTeamsFlow(teams: Set<TeamData>): Flow<List<TaskData>> = flow {
        val result = mutableListOf<TaskData>();
        // TODO: Figure out how to do this with only one GraphQL query. Or at least make these requests concurrently
        teams.forEach {
            val items = Amplify.API
                    .query(ModelQuery.list(TaskData::class.java, TaskData.TEAM.eq(it.id))).data.items
            result.addAll(items)
        }
        emit(result)
    }

    suspend fun getTasksList(): List<TaskData> {
        val items = Amplify.API
                .query(ModelQuery.list(TaskData::class.java)).data.items
        return items.toList()
    }

    fun findByIdFlow(id: String): Flow<TaskData> = flow {
        val taskData = Amplify.API.query(ModelQuery.get(TaskData::class.java, id)).data
        emit(taskData)
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

    suspend fun update(taskData: TaskData) {
        Log.i(TAG, "updating $taskData")
        try {
            val response = Amplify.API.mutate(ModelMutation.update(taskData)).data
            Log.i(TAG, "update successful: $taskData")
        } catch(error: ApiException) {
            Log.e(TAG, "update: " + taskData.toString(), error)
        }
    }
}