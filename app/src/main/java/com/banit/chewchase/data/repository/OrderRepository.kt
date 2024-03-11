package com.banit.chewchase.data.repository

import com.banit.chewchase.data.dao.MenuDAO
import com.banit.chewchase.data.dao.OrderDAO
import com.banit.chewchase.data.dao.OrderFoodsDAO
import com.banit.chewchase.data.entity.Order
import com.banit.chewchase.data.entity.OrderFoods
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val orderDAO: OrderDAO,
    private val menuDAO: MenuDAO,
    private val orderFoodsDAO: OrderFoodsDAO
) {

    suspend fun getAllOrdersForUser(userId: Int) = orderDAO.getUserOrdersWithFoods(userId)
    suspend fun getMenuItemById(menuId: String) = menuDAO.getMenuItemById(menuId)

    suspend fun saveOrderAndFoods(order: Order, orderFoods: List<OrderFoods>) {
        val orderId = orderDAO.insertOrder(order)

        for (food in orderFoods) {
            food.orderFoodsOrderId = orderId.toInt()
            orderFoodsDAO.insertOrderFood(food)
        }
    }

}