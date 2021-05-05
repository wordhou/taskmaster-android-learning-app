package com.edhou.taskmaster.db

import android.util.Log
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.datastore.generated.model.TeamData
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TeamsRepository {
    fun getTeamsListFlow(): Flow<List<TeamData>> = flow {
        val items = Amplify.API.query(ModelQuery.list(TeamData::class.java)).data.items
        emit(items.toList())
    }

    suspend fun getTeamsList(): List<TeamData> {
        val items = Amplify.API
                .query(ModelQuery.list(TeamData::class.java)).data.items
        return items.toList()
    }

    fun findByIdFlow(id: String): Flow<TeamData> = flow {
        val teamData = Amplify.API.query(ModelQuery.get(TeamData::class.java, id)).data
        emit(teamData)
        //emit(Task.fromAmplify(taskData))
        //tasksDao.findById(id).distinctUntilChanged()
    }

    suspend fun findById(id: String): TeamData {
        return Amplify.API.query(ModelQuery.get(TeamData::class.java, id)).data
        //return Task.fromAmplify(Amplify.API.query(ModelQuery.get(TaskData::class.java, id)).data)
        //tasksDao.findById(id).distinctUntilChanged()
    }

    suspend fun insert(teamData: TeamData) {
        Log.i(TasksRepository.TAG, "inserting $teamData")
        try {
            val response = Amplify.API.mutate(ModelMutation.create(teamData)).data
            Log.i(TasksRepository.TAG, "insert successful: $teamData")
        } catch(error: ApiException) {
            Log.e(TasksRepository.TAG, "insert: " + teamData.toString(), error)
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

    suspend fun delete(teamData: TeamData) {
        //tasksDao.delete(Task.fromAmplify(taskData))
        Log.i(TasksRepository.TAG, "deleting $teamData")
        try {
            val response = Amplify.API.mutate(ModelMutation.delete(teamData)).data
            Log.i(TasksRepository.TAG, "delete successful: $teamData")
        } catch(error: ApiException) {
            Log.e(TasksRepository.TAG, "delete: " + teamData.toString(), error)
        }
    }
}