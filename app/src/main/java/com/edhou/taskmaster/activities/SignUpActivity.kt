package com.edhou.taskmaster.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edhou.taskmaster.R
import com.edhou.taskmaster.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
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
    }
}