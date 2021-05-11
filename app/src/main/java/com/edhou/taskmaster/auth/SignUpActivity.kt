package com.edhou.taskmaster.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthException.UsernameExistsException
import com.edhou.taskmaster.R
import dagger.hilt.android.AndroidEntryPoint
import javax.security.auth.login.LoginException

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), AuthViewModel.SignUpHandler {
    private lateinit var signupSubmitButton: Button
    private lateinit var editSignUpEmail: EditText
    private lateinit var editSignUpPassword: EditText
    private val confirmSignUp = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val success = it.data?.getBooleanExtra("confirmation", false)
            Log.i(TAG, "handleSuccess: activity result intent data 'confirmation: $success'")
            finish();
        } else {
            TODO()
        }
    }

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupSubmitButton = findViewById(R.id.submitSignIn)
        editSignUpEmail = findViewById(R.id.editTextSignupEmailAddress);
        editSignUpPassword = findViewById(R.id.editTextSignupPassword);

        signupSubmitButton.setOnClickListener{
            val email = editSignUpEmail.text.toString()
            val password = editSignUpPassword.text.toString()
            authViewModel.signUp(email, password, this)
        }
    }

    override fun handleError(error: AuthException) {
        when(error) {
            is UsernameExistsException -> {
                Log.i(TAG, "handleError: UsernameExistsException")
                Toast.makeText(this, "Username has already been taken. Please choose another username.", Toast.LENGTH_LONG)
            }
            is AuthException.InvalidParameterException -> {
                Log.i(TAG, "handleError: InvalidParameterException")
                Toast.makeText(this, "Password must be 6 characters or longer!", Toast.LENGTH_LONG)
            }
            else -> {
                Log.e(TAG, "handleError: $error")
            }
        }
    }

    override fun confirmSignUp() {
        Log.i(TAG, "confirmSignUp()")
        confirmSignUp.launch(Intent(this@SignUpActivity, ConfirmSignUpActivity::class.java))
    }

    override fun handleSuccess() {
        Log.i(TAG, "handleSuccess: Sign up successful ?!!?!?!")
        finish();
    }

    companion object {
        const val TAG = "SignUpActivity"
    }
}