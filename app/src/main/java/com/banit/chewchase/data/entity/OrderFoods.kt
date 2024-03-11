package com.banit.chewchase.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "order_foods_table",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["orderId"],
            childColumns = ["orderFoodsOrderId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Menu::class,
            parentColumns = ["id"],
            childColumns = ["menuItem"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderFoods(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var orderFoodsOrderId: Int = 0,
    val menuItem: String,
    val quantity: Int
): Serializable
