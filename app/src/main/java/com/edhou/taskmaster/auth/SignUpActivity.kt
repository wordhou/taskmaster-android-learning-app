package com.edhou.taskmaster.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edhou.taskmaster.R
import com.edhou.taskmaster.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), AuthViewModel.OnErrorHandler, AuthViewModel.OnSuccessHandler {
    private lateinit var signupSubmitButton: Button
    private lateinit var editSignUpEmail: EditText
    private lateinit var editSignUpPassword: EditText

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupSubmitButton = findViewById(R.id.submitSignUp)
        editSignUpEmail = findViewById(R.id.editTextSignupEmailAddress);
        editSignUpPassword = findViewById(R.id.editTextSignupPassword);

        signupSubmitButton.setOnClickListener{
            val email = editSignUpEmail.text.toString()
            val password = editSignUpPassword.text.toString()
            authViewModel.signUp(email, password, this, this)
        }
    }

    override fun handleError(resId: String) {
        //Toast.makeText(this, resId, Toast.LENGTH_LONG);
        Toast.makeText(this, "There has been an error in your sign up.", Toast.LENGTH_LONG);
    }

    override fun handleSuccess() {
        finish();
    }

}