package com.banit.chewchase.views.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banit.chewchase.data.entity.User
import com.banit.chewchase.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    // LiveData to handle login state
    private val _loginState = MutableLiveData<User?>()
    val loginState: LiveData<User?> get() = _loginState

    // LiveData to handle registration state
    private val _registrationState = MutableLiveData<Boolean>()
    val registrationState: LiveData<Boolean> get() = _registrationState

    // LiveData to handle error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // Login function
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.loginUser(email, password)
                _loginState.value = user

                if (user == null) {
                    _errorMessage.value = "Invalid email or password"
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }

    // Register function
    fun registerUser(user: User) {
        viewModelScope.launch {
            try {
                val result = userRepository.registerUser(user)
                _registrationState.value = result > 0
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.message}"
            }
        }
    }

    // Clear the error messages when not needed
    fun clearErrors() {
        _errorMessage.value = null
    }
}
