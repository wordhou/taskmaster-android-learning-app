package com.edhou.taskmaster.models

import androidx.room.TypeConverter
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.Status

class TypeConverters {
    @TypeConverter
    fun toStatus(value: Int): Status = enumValues<Status>()[value]

    @TypeConverter
    fun fromStatus(value: Status): Int = value.ordinal

    @TypeConverter
    fun toTimestamp(awsDateTime: Temporal.DateTime) : String {
        return awsDateTime.format()
    }

    @TypeConverter
    fun fromTimestamp(iso8601DateTime: String): Temporal.DateTime {
        return Temporal.DateTime(iso8601DateTime)
    }
}
