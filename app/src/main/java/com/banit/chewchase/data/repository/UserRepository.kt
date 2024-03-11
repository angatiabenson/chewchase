package com.banit.chewchase.data.repository

import com.banit.chewchase.data.dao.UserDAO
import com.banit.chewchase.data.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDAO: UserDAO) {

    // Function to register a new user
    suspend fun registerUser(user: User): Long {
        return userDAO.registerUser(user)
    }

    // Function to validate user login
    suspend fun loginUser(email: String, password: String): User? {
        return userDAO.loginUser(email, password)
    }

}
