package com.banit.chewchase.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val userId: Int,
    val name:String,
    val subtotal: Double,
    val tip: Double,
    val orderDate: String
): Serializable
