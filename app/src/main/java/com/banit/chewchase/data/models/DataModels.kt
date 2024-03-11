package com.banit.chewchase.data.models

import androidx.room.Embedded
import androidx.room.Relation
import com.banit.chewchase.data.entity.Menu
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.entity.OrderFoods
import java.io.Serializable

data class FoodDetailsFromOrder(
    val orderFoodsOrderId: Int,
    val quantity: Int,
    val name: String,
    val description: String,
    val price: Double
):Serializable

data class UserOrdersWithFoods(
    @Embedded val order: Order,
    @Relation(
        entity = OrderFoods::class,
        parentColumn = "orderId",
        entityColumn = "orderFoodsOrderId"
    )
    val foods: List<OrderFoodsWithMenu>
):Serializable


data class CartItem(
    val menu: Menu,
    var quantity: Int
)

data class OrderFoodsWithMenu(
    @Embedded val orderFoods: OrderFoods,
    @Relation(
        parentColumn = "menuItem",
        entityColumn = "id"
    )
    val menu: Menu
):Serializable
