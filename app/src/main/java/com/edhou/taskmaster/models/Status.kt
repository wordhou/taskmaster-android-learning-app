package com.edhou.taskmaster.models

import android.content.res.Resources
import androidx.room.TypeConverter
import com.edhou.taskmaster.R

enum class Status {
    NEW {
        override fun getString(resources: Resources): String {
            return resources.getString(R.string.status_new)
        }
    },
    ASSIGNED {
        override fun getString(resources: Resources): String {
            return resources.getString(R.string.status_assigned)
        }
    },
    IN_PROGRESS {
        override fun getString(resources: Resources): String {
            return resources.getString(R.string.status_in_progress)
        }
    },
    COMPLETE {
        override fun getString(resources: Resources): String {
            return resources.getString(R.string.status_complete)
        }
    };

    abstract fun getString(resources: Resources): String
}

class StatusConverters {
    @TypeConverter
    fun toStatus(value: Int) : Status = enumValues<Status>()[value]
    @TypeConverter
    fun fromStatus(value: Status): Int = value.ordinal
}