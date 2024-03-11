package com.banit.chewchase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val userName: String,
    val userEmail: String,
    val userPassword: String,
    val userPhone: String? = null,
)
