package com.edhou.taskmaster.models

import androidx.room.TypeConverter
import com.amplifyframework.datastore.generated.model.Status

class StatusConverters {
    @TypeConverter
    fun toStatus(value: Int): Status = enumValues<Status>()[value]

    @TypeConverter
    fun fromStatus(value: Status): Int = value.ordinal
}
