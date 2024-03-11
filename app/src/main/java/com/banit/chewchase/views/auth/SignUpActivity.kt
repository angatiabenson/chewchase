package com.banit.chewchase.views.auth

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.banit.chewchase.data.entity.User
import com.banit.chewchase.databinding.ActivitySignUpBinding
import com.banit.chewchase.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mContext: Context
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)

        // Setup observers
        setupObservers()

        // Setup button click listener
        binding.btnSignUp.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        if (binding.edName.text.toString().isEmpty()) {
            binding.edNameLayout.error = "Name Required!"
            return
        }

        if (binding.edEmail.text.toString().isEmpty()) {
            binding.edEmailLayout.error = "Email Required!"
            return
        }

        if (binding.edPhone.text.toString().isEmpty()) {
            binding.edPhoneLayout.error = "Phone Required!"
            return
        }

        if (binding.edPassword.text.toString().isEmpty()) {
            binding.edPasswordLayout.error = "Password Required!"
            return
        }

        val user = User(
            userName = binding.edName.text.toString(),
            userEmail = binding.edEmail.text.toString(),
            userPassword = binding.edPassword.text.toString(),
            userPhone = binding.edPhone.text.toString()
        )

        // Clear previous errors
        viewModel.clearErrors()

        viewModel.registerUser(user)
    }

    private fun setupObservers() {
        viewModel.registrationState.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration was successful. Navigate back to login or to the main activity.
                DialogUtils.toast("Registration Successful!")
                finish()
            } else {
                DialogUtils.toast("Registration failed!")
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                DialogUtils.toast(errorMessage)
            }
        }
    }
}