package com.edhou.taskmaster.models

import com.amplifyframework.core.model.temporal.Temporal

data class Team (
        val id: String,
        val createdAt: Temporal.DateTime,
        val updatedAt: Temporal.DateTime
        ) {
}
