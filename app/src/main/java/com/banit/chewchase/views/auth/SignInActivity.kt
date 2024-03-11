package com.banit.chewchase.views.auth

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.banit.chewchase.databinding.ActivitySignInBinding
import com.banit.chewchase.utils.DialogUtils
import com.banit.chewchase.utils.PrefManager
import com.banit.chewchase.utils.loadActivity
import com.banit.chewchase.views.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var mContext: Context
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        mContext = this
        setContentView(binding.root)

        // Setup observers
        setupObservers()

        // Setup button click listeners
        binding.btnSignIn.setOnClickListener {
            signIn()
        }

        binding.txtSignUp.setOnClickListener {
            loadActivity(mContext, SignUpActivity::class.java)
        }

    }

    private fun setupObservers() {
        viewModel.loginState.observe(this) { user ->
            if (user != null) {
                //save the user details
                PrefManager().setLoggedIn(user.userId.toString())
                // Navigate to main activity or whatever your app's home screen is
                loadActivity(mContext, MainActivity::class.java)
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                DialogUtils.toast(errorMessage)
            }
        }
    }

    private fun signIn() {
        if (binding.edEmail.text.toString().isEmpty()) {
            binding.edEmailLayout.error = "Email Required!"
            return
        }

        if (binding.edPassword.text.toString().isEmpty()) {
            binding.edPasswordLayout.error = "Password Required!"
            return
        }

        val email = binding.edEmail.text.toString()
        val password = binding.edPassword.text.toString()

        // Clear previous errors
        viewModel.clearErrors()

        viewModel.loginUser(email, password)
    }
}