package com.edhou.taskmaster

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.edhou.taskmaster.db.AppDatabase
import com.edhou.taskmaster.db.TasksRepository
import com.edhou.taskmaster.db.TeamsRepository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TaskmasterApplication : Application() {
    override fun onCreate() {
        Log.i("App", "Creating application context")
        super.onCreate()
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)
            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", e)
        }
    }
}