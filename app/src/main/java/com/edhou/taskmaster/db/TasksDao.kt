package com.edhou.taskmaster.db

import androidx.room.*
import com.edhou.taskmaster.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Query("SELECT * FROM Task")
    fun getTasksList(): Flow<List<Task>>

    @Query("SELECT * FROM Task where id == :id")
    fun findById(id: String): Flow<Task>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}