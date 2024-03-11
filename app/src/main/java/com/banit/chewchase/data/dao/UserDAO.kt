package com.banit.chewchase.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banit.chewchase.data.entity.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: User): Long

    @Query("SELECT * FROM user_table WHERE userEmail = :email AND userPassword = :password")
    suspend fun loginUser(email: String, password: String): User?

}
