package com.edhou.taskmaster.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Status
import com.amplifyframework.datastore.generated.model.TaskData

@Entity
data class Task(
        @PrimaryKey(autoGenerate = false)
        var id: String,
        val name: String,
        val description: String,
        val status: Status,
        val createdAt: Temporal.DateTime?,
        val updatedAt: Temporal.DateTime?) {

    fun amplify(): TaskData {
        return TaskData.builder()
                .name(this.name)
                .id(this.id)
                .description(this.description)
                .status(this.status)
                .build()
    }

    companion object {
        fun fromAmplify(taskData: TaskData): Task {
            return Task(id = taskData.id,
                    name = taskData.name,
                    description = taskData.description,
                    status = taskData.status,
                    createdAt = taskData.createdAt,
                    updatedAt = taskData.updatedAt)
        }
    }
}