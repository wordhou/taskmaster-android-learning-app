package com.edhou.taskmaster.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.TeamData

@Entity
data class Team (
        @PrimaryKey(autoGenerate = false)
        val id: String,
        val name: String,
        val createdAt: Temporal.DateTime?,
        val updatedAt: Temporal.DateTime?
        ) {

        fun amplify(): TeamData {
                return TeamData.builder()
                        .name(this.name)
                        .id(this.id)
                        .build()
        }
}