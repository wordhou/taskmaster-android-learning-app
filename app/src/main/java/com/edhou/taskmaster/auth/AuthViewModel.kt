package com.edhou.taskmaster.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.kotlin.core.Amplify
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    fun signUp(email: String,
               password: String,
               onSuccessHandler: OnSuccessHandler,
               onErrorHandler: OnErrorHandler) {
        viewModelScope.launch {
            try {
                val result = Amplify.Auth.signUp(email, password)
                Log.i(TAG, "signUp: $result")
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Sign in succeeded")
                    onSuccessHandler.handleSuccess()
                } else {
                    Log.e("AuthQuickstart", "Sign in not complete")
                    onErrorHandler.handleError("bah");
                }
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign in failed", error)
            }
        }
    }

    interface OnErrorHandler {
        fun handleError(resId: String)
    }

    interface OnSuccessHandler {
        fun handleSuccess()
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}