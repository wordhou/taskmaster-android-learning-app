package com.edhou.taskmaster.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.amplifyframework.auth.AuthException
import com.edhou.taskmaster.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmSignUpActivity : AppCompatActivity(), AuthViewModel.ConfirmationHandler {
    private lateinit var submitSignUpConfirmation: Button
    private lateinit var editConfirmationEmail: EditText
    private lateinit var editConfirmationCode: EditText

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up)

        submitSignUpConfirmation = findViewById(R.id.submitSignUpConfirmation)
        editConfirmationEmail = findViewById(R.id.editTextConfirmSignUpEmail)
        editConfirmationCode = findViewById(R.id.editTextConfirmationCode)

        submitSignUpConfirmation.setOnClickListener{
            val email = editConfirmationEmail.text.toString()
            val code = editConfirmationCode.text.toString()
            authViewModel.confirm(email, code, this)
        }
    }

    override fun handleError(error: AuthException) {
        TODO("Not yet implemented")
    }

    override fun handleSignUpComplete() {
        Log.i(TAG, "handleSignUpComplete:")
        val returnIntent = Intent()
        returnIntent.putExtra("confirmation", true);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    override fun handleSignUpIncomplete() {
        TODO("What to do??")
    }

    companion object {
        private val TAG = "ConfirmSignUpActivity"
    }
}