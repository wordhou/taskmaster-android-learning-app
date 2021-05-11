package com.edhou.taskmaster.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignInOptions
import com.amplifyframework.auth.options.AuthSignOutOptions
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.auth.result.step.AuthSignInStep
import com.amplifyframework.auth.result.step.AuthSignUpStep
import com.amplifyframework.kotlin.core.Amplify
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    fun signUp(email: String,
               password: String,
               handler: SignUpHandler) {
        viewModelScope.launch {
            try {
                val options = AuthSignUpOptions.builder()
                        .userAttribute(AuthUserAttributeKey.email(), email)
                        .build()
                val result = Amplify.Auth.signUp(email, password, options)
                Log.i(TAG, "signUp: $result")
                when(result.nextStep.signUpStep) {
                    AuthSignUpStep.CONFIRM_SIGN_UP_STEP -> {
                        Log.i("AuthQuickstart", "1st step successful, Confirm sign up step")
                        handler.confirmSignUp()
                    }
                    AuthSignUpStep.DONE -> {
                        Log.i("AuthQuickstart", "Sign up succeeded!")
                        handler.handleSuccess()
                    }
                }
            } catch (error: AuthException) {
                handler.handleError(error);
            }
        }
    }

    fun signIn(email: String, password: String, handler: SignInHandler) {
        viewModelScope.launch {
            try {
                val result = Amplify.Auth.signIn(email, password)
                Log.i(TAG, "signUp: $result")
                Log.i(TAG, "signUp: ${result.nextStep.signInStep}")
                when (result.nextStep.signInStep) {
                    AuthSignInStep.DONE -> {
                        Log.i(TAG, "signIn: sign in complete")
                        handler.signInComplete()
                    }
                    AuthSignInStep.CONFIRM_SIGN_UP -> {
                        Log.i(TAG, "signIn: confirm sign up")
                        handler.confirmSignUp()
                    }
                    AuthSignInStep.CONFIRM_SIGN_IN_WITH_NEW_PASSWORD -> {
                        Log.i(TAG, "signIn: sign in with new password")
                        handler.confirmSignInWithNewPassword()
                    }
                    AuthSignInStep.RESET_PASSWORD -> {
                        Log.i(TAG, "signIn: reset password")
                        handler.resetPassword()
                    }
                    else -> {
                        Log.i(TAG, "signIn: ${result.nextStep.signInStep}")
                        Log.i(TAG, "signIn: ${result.nextStep}")
                        Log.i(TAG, "signIn: ${result}")
                    }
                }
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign in failed", error)
                handler.handleError(error);
            }
        }
    }

    fun confirm(email: String, code: String, handler: ConfirmationHandler) {
        viewModelScope.launch {
            try {
                val result = Amplify.Auth.confirmSignUp(email, code)
                Log.i(TAG, "signUp: $result")
                if (result.isSignUpComplete) {
                    Log.i("AuthQuickstart", "Sign up confirmation succeeded")
                    handler.handleSignUpComplete()
                } else {
                    Log.e("AuthQuickstart", "Sign in not complete")
                    handler.handleSignUpIncomplete();
                }
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign confirmation failed", error)
                handler.handleError(error);
            }
        }
    }

    fun signOut(handler: SignOutHandler) {
        viewModelScope.launch {
            try {
                val options = AuthSignOutOptions.builder().globalSignOut(true).build()
                val result = Amplify.Auth.signOut(options)
                Log.i("AuthQuickstart", "Logging out result: $result");
                handler.handleSuccess()
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "sign out failed", error)
                handler.handleError(error);
            }
        }
    }

    interface SignOutHandler {
        fun handleError(error: AuthException)
        fun handleSuccess()
    }

    interface SignUpHandler {
        fun handleError(error: AuthException)
        fun confirmSignUp()
        fun handleSuccess()
    }

    interface SignInHandler {
        fun handleError(error: AuthException)
        fun signInComplete()
        fun confirmSignInWithNewPassword()
        fun resetPassword()
        fun confirmSignUp()
    }

    interface ConfirmationHandler {
        fun handleError(error: AuthException)
        fun handleSignUpComplete()
        fun handleSignUpIncomplete()
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}