package com.edhou.taskmaster.db

import androidx.room.*
import com.edhou.taskmaster.models.Task

@Dao
interface TasksDao {
    @Query("SELECT * FROM Task")
    fun getTasksList(): List<Task>

    @Query("SELECT * FROM Task where id == :id")
    fun findById(id: Long): Task

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}