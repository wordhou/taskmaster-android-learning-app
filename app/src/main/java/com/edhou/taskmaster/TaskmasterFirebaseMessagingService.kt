package com.edhou.taskmaster

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TaskmasterFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)

        Log.d(TAG, "onMessageReceived: ${msg.data.toString()}")

        msg.notification?.let {
            Log.i(TAG, "Message title: ${it.title}")
            Log.i(TAG, "Message body: ${it.body}")
            Toast.makeText(this, getString(R.string.notification_received, it.body), Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        const val TAG = "FirebaseMessagingSvc"
    }
}