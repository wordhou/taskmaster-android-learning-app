package com.edhou.taskmaster

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TaskmasterFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)

        Log.d(TAG, "onMessageReceived: ${msg.data.toString()}")

        msg.notification?.let {
            Log.i(TAG, "Message title: ${it.title}")
            Log.i(TAG, "Message body: ${it.body}")
        }
    }

    companion object {
        const val TAG = "FirebaseMessagingSvc"
    }
}