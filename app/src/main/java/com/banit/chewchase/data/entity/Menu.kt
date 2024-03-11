package com.banit.chewchase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "menu_table")
data class Menu(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double
): Serializable
