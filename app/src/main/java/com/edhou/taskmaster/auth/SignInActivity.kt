package com.edhou.taskmaster.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.amplifyframework.auth.AuthException
import com.edhou.taskmaster.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sign

@AndroidEntryPoint
class SignInActivity : AppCompatActivity(), AuthViewModel.SignInHandler {
    private lateinit var signInSubmitButton: Button
    private lateinit var editSignInEmail: EditText
    private lateinit var editSignInPassword: EditText
    private val confirmSignUp = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val success = it.data?.getBooleanExtra("confirmation", false)
            Log.i(SignUpActivity.TAG, "handleSuccess: activity result intent data 'confirmation: $success'")
            Toast.makeText(this, "Confirmed!", Toast.LENGTH_LONG)
            finish();
        } else {
            TODO()
        }
    }

    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInSubmitButton= findViewById(R.id.submitSignIn)
        editSignInEmail = findViewById(R.id.editTextSignInEmail);
        editSignInPassword = findViewById(R.id.editTextSignInPassword);

        signInSubmitButton.setOnClickListener {
            val email = editSignInEmail.text.toString()
            val password = editSignInPassword.text.toString()
            authViewModel.signIn(email, password, this)
        }
    }

    override fun handleError(error: AuthException) {
        when(error) {
            is AuthException.UserNotConfirmedException -> {
                Log.i(SignUpActivity.TAG, "confirmSignUp()")
                confirmSignUp.launch(Intent(this@SignInActivity, ConfirmSignUpActivity::class.java))
            }
        }
    }

    override fun signInComplete() {
        finish();
    }

    override fun confirmSignInWithNewPassword() {
        TODO("Not yet implemented")
    }

    override fun resetPassword() {
        TODO("Not yet implemented")
    }

    override fun confirmSignUp() {
        Log.i(SignUpActivity.TAG, "confirmSignUp()")
        confirmSignUp.launch(Intent(this@SignInActivity, ConfirmSignUpActivity::class.java))
    }
}