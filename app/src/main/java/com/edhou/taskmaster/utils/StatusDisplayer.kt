package com.edhou.taskmaster.utils

import android.content.res.Resources
import com.amplifyframework.datastore.generated.model.Status
import com.edhou.taskmaster.R

class StatusDisplayer {
    companion object {
        fun statusToString(status: Status, resources: Resources): String {
            return when(status) {
                Status.ASSIGNED -> resources.getString(R.string.status_assigned)
                Status.NEW -> resources.getString(R.string.status_new)
                Status.IN_PROGRESS -> resources.getString(R.string.status_in_progress)
                Status.COMPLETE -> resources.getString(R.string.status_complete)
            }
        }
    }
}